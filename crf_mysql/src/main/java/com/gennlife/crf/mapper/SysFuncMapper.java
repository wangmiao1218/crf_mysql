package com.gennlife.crf.mapper;

import java.util.List;
import java.util.Map;

import com.gennlife.crf.bean.SysFuncBean;


public interface SysFuncMapper {
	
    /** 
    * @Title: selectSysFuncByOpId 
    * @Description: 根据OP_ID，查询菜单列表数据
    * @param: @param map
    * @param: @return
    * @param: @throws Exception :
    * @return: List<SysFuncBean>
    * @throws 
    */
    public List<SysFuncBean> selectSysFuncByOpId(Map<String,Object> map) throws Exception;
	
}
