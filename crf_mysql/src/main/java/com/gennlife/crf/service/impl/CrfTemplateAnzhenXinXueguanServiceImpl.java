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

	@Override
	//通用service验证联动字段
	public void verifyLinkageFieldGeneralServiceMethod(PhantomJSDriver driver,String baseName) throws Exception{
		// 获取所有就诊－住院与诊断list
		List<CrfTemplateAnzhenXinXueguan> list = crfTemplateAnzhenXinXueguanMapper
				.getCrfTemplateAnzhenXinXueguanListByBaseName(baseName);
		
		// 循环list
		for (int i = 0; i < list.size(); i++) {
			//判断DisplayMainKey是否为空，不为空则继续判断
			if (list.get(i).getDisplayMainKey()!=null) {
				//不为空，去页面查看是否存在
				//存在,则结果直接为no
				if (SeleniumUtils.isElementPresent(driver,list.get(i).getIdXpath())) {
					list.get(i).setLinkageResult("no");
				}else {//不存在
					//则获取对应的联动字段名称
					String linkageEnglishName = ListAndStringUtils.displayMainKeyToEnglishName(list.get(i).getDisplayMainKey());
					//在list中查英文名称为linkageEnglishName的CrfTemplateAnzhenXinXueguan
					CrfTemplateAnzhenXinXueguan linkageCrf = ListAndStringUtils.searchCrfListReturnOneCrf(list, linkageEnglishName);
					//页面中设置联动字段为对应选项值
					new Select(driver.findElementById(linkageCrf.getIdXpath())).selectByValue(list.get(i).getDisplayMainValue());
					//检查是否存在字段
					//存在,则结果直接为pass
					if (SeleniumUtils.isElementPresent(driver,list.get(i).getIdXpath())) {
						list.get(i).setLinkageResult("pass");
					}else {//不存在,则结果直接no
						list.get(i).setLinkageResult("no");
					}
					
					//判断之后跟选项归位，以防影响后面元素判断
					new Select(driver.findElementById(linkageCrf.getIdXpath())).selectByIndex(0);
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
