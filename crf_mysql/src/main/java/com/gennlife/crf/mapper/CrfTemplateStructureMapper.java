package com.gennlife.crf.mapper;

import java.util.List;

import com.gennlife.crf.bean.CrfTemplateStructure;

/**
 * @Description: CrfTemplateStructureMapper相关接口
 * @author: wangmiao
 * @Date: 2017年9月25日 下午4:04:27 
 */
public interface CrfTemplateStructureMapper {
	
    
    /** 
     * @Title: getCrfTemplateStructureListByHospitalDepartment 
     * @Description: 根据HospitalDepartment查询列表
     * @param: String hospitalDepartment
     * @param: @throws Exception :
     * @return: List<CrfTemplateStructure>
     * @throws 
     */
    public List<CrfTemplateStructure> getCrfTemplateStructureListByHospitalDepartment(String hospitalDepartment) throws Exception;
    

}