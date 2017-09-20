package com.gennlife.crf.service.impl;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gennlife.crf.bean.CrfTemplateAnzhenXinXueguan;
import com.gennlife.crf.mapper.CrfTemplateAnzhenXinXueguanMapper;
import com.gennlife.crf.service.CrfTemplateAnzhenXinXueguanService;
import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.crf.utils.SeleniumUtils;

/**
 * @Description: 获取CrfTemplateAnzhenXinXueguan的impl
 * @author: wangmiao
 * @Date: 2017年9月19日 上午9:36:22 
 */
@Service
public class CrfTemplateAnzhenXinXueguanServiceImpl implements CrfTemplateAnzhenXinXueguanService {

	@Autowired
	private CrfTemplateAnzhenXinXueguanMapper crfTemplateAnzhenXinXueguanMapper;

	/**
	 * 注意：巨大坑
	 */
	@Override
	//通用service验证联动字段
	public void verifyLinkageFieldGeneralServiceMethod(PhantomJSDriver driver,String baseName) throws Exception{
		// 获取所有就诊－住院与诊断list
		List<CrfTemplateAnzhenXinXueguan> list = crfTemplateAnzhenXinXueguanMapper
				.getCrfTemplateAnzhenXinXueguanListByBaseName(baseName);
		// 循环list
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getChineseName());
			//判断DisplayMainKey是否为空，不为空则继续判断
			//注意坑：可能取出值为空""
			if (list.get(i).getDisplayMainKey()!=null && !"".equals(list.get(i).getDisplayMainKey()) && !" ".equals(list.get(i).getDisplayMainKey())) {
				//不为空，去页面查看是否存在
				//存在,则结果直接为no
				if (SeleniumUtils.isElementPresent(driver,list.get(i).getIdXpath())) {
					list.get(i).setLinkageResult("no");
				}else {//不存在 
					//==========================
					//注意：多级联动的大坑
					//获取对应的联动字段名称
					String linkageEnglishName1 = ListAndStringUtils.displayMainKeyToEnglishName(list.get(i).getDisplayMainKey());
					//在list中查英文名称为linkageEnglishName的CrfTemplateAnzhenXinXueguan
					CrfTemplateAnzhenXinXueguan linkageCrf1 = ListAndStringUtils.searchCrfListReturnOneCrf(list, linkageEnglishName1);
					
					//！！判断linkageCrf1是否存在联动路径
					//如果为空，则为两层联动，执行下面两层的逻辑
					if (linkageCrf1.getDisplayMainKey()==null || "".equals(linkageCrf1.getDisplayMainKey()) || " ".equals(linkageCrf1.getDisplayMainKey())) {
						//===================二层逻辑============================
						//页面中设置联动字段为对应选项值
						Boolean b = SeleniumUtils.isSelectByValuePresent(driver, linkageCrf1.getIdXpath(), 
										ListAndStringUtils.displayMainValueToSelectByValue(list.get(i).getDisplayMainValue()));
						if (b) {
							new Select(driver.findElementById(linkageCrf1.getIdXpath())).
									selectByValue(ListAndStringUtils.displayMainValueToSelectByValue(list.get(i).getDisplayMainValue()));
							//检查是否存在字段
							//存在,则结果直接为pass
							if (SeleniumUtils.isElementPresent(driver,list.get(i).getIdXpath())) {
								list.get(i).setLinkageResult("pass");
							}else {//不存在,则结果直接no
								list.get(i).setLinkageResult("no");
							}
							//判断之后跟选项归位，以防影响后面元素判断
							new Select(driver.findElementById(linkageCrf1.getIdXpath())).selectByIndex(0);
						}else {
							list.get(i).setLinkageResult("no");
						}
						//===================二层逻辑结束============================
						
					}else if (linkageCrf1.getDisplayMainKey()!=null && !"".equals(linkageCrf1.getDisplayMainKey()) && !" ".equals(linkageCrf1.getDisplayMainKey())) {
						//如果不为空，则为多层联动，执行下面三层的逻辑
						//获取对应的联动字段名称
						String linkageEnglishName2 = ListAndStringUtils.displayMainKeyToEnglishName(linkageCrf1.getDisplayMainKey());
						//在list中查英文名称为linkageEnglishName2的CrfTemplateAnzhenXinXueguan
						CrfTemplateAnzhenXinXueguan linkageCrf2 = ListAndStringUtils.searchCrfListReturnOneCrf(list, linkageEnglishName2);
						//如果为空，则为三层联动，执行下面三层的逻辑
						if (linkageCrf2.getDisplayMainKey()==null || "".equals(linkageCrf2.getDisplayMainKey()) || " ".equals(linkageCrf2.getDisplayMainKey())) {
							//===================三层逻辑============================
							//即：开启linkageCrf2，再开启linkageCrf1（关闭则逆序）
							//1.先判断linkageCrf2是否能选择
							//页面中设置联动字段为对应选项值,不能则直接no
							Boolean bb = SeleniumUtils.isSelectByValuePresent(driver, linkageCrf2.getIdXpath(),
											ListAndStringUtils.displayMainValueToSelectByValue(linkageCrf1.getDisplayMainValue()));
							if (bb) {
								//2.开启linkageCrf2
								new Select(driver.findElementById(linkageCrf2.getIdXpath())).
										selectByValue(ListAndStringUtils.displayMainValueToSelectByValue(linkageCrf1.getDisplayMainValue()));
								//3.检查linkageCrf1是否存在
								if (SeleniumUtils.isElementPresent(driver,linkageCrf1.getIdXpath())) {
									//4.存在，则判断linkageCrf1能否设置联动字段
									//页面中设置联动字段为对应选项值
									Boolean bbb = SeleniumUtils.isSelectByValuePresent(driver, linkageCrf1.getIdXpath(), 
											ListAndStringUtils.displayMainValueToSelectByValue(list.get(i).getDisplayMainValue()));
									if (bbb) {
										new Select(driver.findElementById(linkageCrf1.getIdXpath())).
												selectByValue(ListAndStringUtils.displayMainValueToSelectByValue(list.get(i).getDisplayMainValue()));
										//5.检查是否存在字段
										//存在,则结果直接为pass
										if (SeleniumUtils.isElementPresent(driver,list.get(i).getIdXpath())) {
											list.get(i).setLinkageResult("pass");
										}else {//不存在,则结果直接no
											list.get(i).setLinkageResult("no");
										}
										//6.判断之后跟选项归位，以防影响后面元素判断(需要逆序关闭，则关闭linkageCrf1，再关闭linkageCrf2)
										new Select(driver.findElementById(linkageCrf1.getIdXpath())).selectByIndex(0);
									}else {
										list.get(i).setLinkageResult("no");
									}
								}else {//不存在,则结果直接no
									list.get(i).setLinkageResult("no");
								}

								//判断之后跟选项归位，以防影响后面元素判断(需要逆序关闭，则关闭linkageCrf1，再关闭linkageCrf2)
								new Select(driver.findElementById(linkageCrf2.getIdXpath())).selectByIndex(0);
								
							}else {
								list.get(i).setLinkageResult("no");
							}
							
						}else {//若不为空，则直接写"大于三层，请人工测试"
							list.get(i).setLinkageResult("大于三层，请人工测试");
						}
						//===================三层逻辑结束============================
					}
					
				}
				
				//更新数据库
				crfTemplateAnzhenXinXueguanMapper.updateCrfTemplateAnzhenXinXueguan(list.get(i));
			}
		}
		Thread.sleep(1000);
	}
	
	
	
	@Override
	public List<CrfTemplateAnzhenXinXueguan> getCrfTemplateAnzhenXinXueguanList(
			Map<String, Object> map) throws Exception {
		return crfTemplateAnzhenXinXueguanMapper.getCrfTemplateAnzhenXinXueguanList(map);
	}

	@Override
	public List<CrfTemplateAnzhenXinXueguan> getCrfTemplateAnzhenXinXueguanListByVariableType(
			String variableType) throws Exception {
		return crfTemplateAnzhenXinXueguanMapper.getCrfTemplateAnzhenXinXueguanListByVariableType(variableType);
	}

	@Override
	public CrfTemplateAnzhenXinXueguan getCrfTemplateAnzhenXinXueguanByEnglishName(
			String englishName) throws Exception {
		return crfTemplateAnzhenXinXueguanMapper.getCrfTemplateAnzhenXinXueguanByEnglishName(englishName);
	}

	@Override
	public List<CrfTemplateAnzhenXinXueguan> getCrfTemplateAnzhenXinXueguanListByBaseName(
			String baseName) throws Exception {
		return crfTemplateAnzhenXinXueguanMapper.getCrfTemplateAnzhenXinXueguanListByBaseName(baseName);
	}

	@Override
	public int updateCrfTemplateAnzhenXinXueguan(
			CrfTemplateAnzhenXinXueguan CrfTemplateAnzhenXinXueguan)
			throws Exception {
		return crfTemplateAnzhenXinXueguanMapper.updateCrfTemplateAnzhenXinXueguan(CrfTemplateAnzhenXinXueguan);
	}

	
}
