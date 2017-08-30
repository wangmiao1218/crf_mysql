package com.gennlife.mengyujie;

import java.util.List;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;

/**
 * @Description:
 * @author: wangmiao
 * @Date: 2017年8月30日 上午9:16:08
 */
public class WriteSchemaCrfTemplateOfMyj {

	public static void writeSchema(Excel excelmb,Excel excel) {
		Integer chNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excelmb, 0, "中文名称");
		Integer enNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excelmb, 0, "英文名称");
		Integer groupInfoCellNum = ExcelUtils.searchKeyWordOfOneLine(excelmb, 0, "组结构信息");
		
		//获取中文名称一列
		List<String> list = ExcelUtils.readExcelOfList(excelmb, chNameCellNum);
		
		//除去表头开始遍历
		for (int i = 1; i < list.size(); i++) {
			//设置excel的sheet值
			excel.setSheetName(list.get(i));
			//判断excel是否存在该sheet
			if (ExcelUtils.checkSheetOfExcelExist(excel)) {
				//继续判断为两组还是三组
				Integer rowNum = ExcelUtils.searchKeyWordOfList(excelmb, chNameCellNum, list.get(i));
				String groupInfo = ExcelUtils.readContent(excelmb, rowNum, groupInfoCellNum);
				//获取表名
				String tableName = ExcelUtils.readContent(excelmb, rowNum, enNameCellNum);
				
				if ("两组".equals(groupInfo)) {
					//转到第二组的逻辑
					System.out.println("两组");
					writeSchemaOfTwoGroups(excel,tableName);
				}
				
				if ("三组".equals(groupInfo)) {
					//转到第三组的逻辑
					System.out.println("三组");
					writeSchemaOfThreeGroups(excel,tableName);
				}
			}
			
			//如果对应的sheet不存在则继续
			if (!ExcelUtils.checkSheetOfExcelExist(excel)) {
				continue;
			}
		}
		
	}
	
	
	public static void writeSchemaOfTwoGroups(Excel excel,String tableName) {
		
		
	}
	
	
	public static void writeSchemaOfThreeGroups(Excel excel,String tableName) {
		
		
	}
	

}
