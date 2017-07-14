package com.gennlife.crf.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gennlife.crf.bean.CrfTemplate;
import com.gennlife.crf.bean.SysOp;
import com.gennlife.crf.service.CrfTemplateService;
import com.gennlife.crf.service.LoginService;
import com.gennlife.crf.utils.VerifyCodeUtil;

/**
 * @Description: 用户登录控制
 * @author: wangmiao
 * @Date: 2017年6月14日 下午6:22:02
 */
@Controller
@RequestMapping("logincontroller")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CrfTemplateService crfTemplateService;
	
	

	/**
	 * @Title: getVerifyCode
	 * @Description: 获取验证码
	 * @param: HttpServletResponse response
	 * @param: HttpSession session
	 * @param: @throws IOException
	 * @throws
	 */
	@RequestMapping("getVerifyCode")
	public void getVerifyCode(HttpServletResponse response, HttpSession session)
			throws IOException {
		// 设置页面不缓存
		// HTTP 1.1
		response.setHeader("Pragma", "no-cache");// HTTP 1.1
		// HTTP 1.0
		response.setHeader("Cache-Control", "no-cache");
		// 设置缓存时间，0就是不缓存
		response.setDateHeader("Expires", 0);

		String verifyCode = VerifyCodeUtil.generateTextCode(
				VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);
		session.setAttribute("verifyCode", verifyCode);
		logger.debug("已将验证码放入到session中：【" + verifyCode + "】");

		response.setContentType("image/jpeg");
		BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(
				verifyCode, 90, 30, 5, true, Color.WHITE, Color.BLACK, null);

		ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());

	}

	/**
	 * @Title: login
	 * @Description: 登录(不使用shiro)
	 * @param: String username
	 * @param: String password
	 * @param: String verifyCode
	 * @param: HttpSession session
	 * @param: RedirectAttributes redirectAttributes
	 * @return: String
	 * @throws
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
			String password, String verifyCode, HttpSession session,
			RedirectAttributes redirectAttributes) throws Exception {

		logger.debug("登陆参数为" + username + "---" + password + "---" + verifyCode);

		String sessionVerifyCode = (String) session.getAttribute("verifyCode");
		if (!verifyCode.equals(sessionVerifyCode)) {
			logger.debug("验证码不正确");
			redirectAttributes.addFlashAttribute("errMsg", "验证码不正确");
			return "redirect:/login";
		}

		// 用户名密码
		SysOp param = new SysOp();
		param.setLoginName(username);
		param.setLoginPasswd(password);

		// 后台查询
		SysOp sysOp = loginService.getSysOpByUnameAndPwd(param);
		System.out.println(sysOp);
		if (sysOp == null) {
			logger.debug("用户名密码不正确");
			redirectAttributes.addFlashAttribute("errMsg", "用户名密码不正确");
			return "redirect:/login";
		}

		// 登录用户信息放到session中
		session.setAttribute("currentUser", sysOp);

		// 成功返回主页
		//测试return "redirect:/page/list.html";
		return "redirect:/index";

	}

	
	/** 
	* @Title: getMenu 
	* @Description: 获取CrfTemplate列表,获取菜单
	* @param: @param session
	* @param: @return
	* @param: @throws Exception :
	* @return: List<CrfTemplate>
	* @throws 
	*/
	@ResponseBody
	@RequestMapping(value = "getMenu", method = RequestMethod.GET)
	public List<CrfTemplate> getMenu(HttpSession session,Map<String, Object> map) throws Exception {

		// 执行查询
		List<CrfTemplate> list = crfTemplateService.getCrfTemplateList(map);
		map.put("newList", list);
		// 返回
		return list;
	}

	
	/*@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo",required=false) String pageNoStr,
			Map<String, Object> map){
		int pageNo=1;
		
		try {
			pageNo=Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}
		
		Page<User> page = userService.getPage(pageNo);
		map.put("page", page);
		
		return "page/list";
	}*/
}
