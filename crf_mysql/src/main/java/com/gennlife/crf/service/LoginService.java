package com.gennlife.crf.service;

import java.util.List;
import java.util.Map;

import com.gennlife.crf.bean.SysFuncBean;
import com.gennlife.crf.bean.SysOp;

/**
 * @Description: 登录的接口
 * @author: wangmiao
 * @Date: 2017年7月14日 上午9:13:18 
 */
public interface LoginService {
	/**
	 * @Title: selectSysOpByUnameAndPwd
	 * @Description: 根据用户名密码查询
	 * @param: SysOp sysOp
	 * @param: @throws Exception :
	 * @return: SysOp
	 * @throws
	 */
	public SysOp selectSysOpByUnameAndPwd(SysOp sysOp) throws Exception;
	
	
    /** 
    * @Title: selectSysFuncList 
    * @Description: 查询菜单列表数据
    * @param: @param map
    * @param: @return
    * @param: @throws Exception :
    * @return: List<SysFuncBean>
    * @throws 
    */
    public List<SysFuncBean> selectSysFuncList(Map<String, Object> map) throws Exception;
	

}
