package com.gennlife.crf.mapper;

import com.gennlife.crf.bean.SysOp;

/**
 * @Description: 查询用户的接口
 * @author: wangmiao
 * @Date: 2017年6月14日 下午6:47:05 
 */
public interface SysOpMapper {
	
    /** 
    * @Title: getSysOpByUnameAndPwd 
    * @Description: 根据用户名密码查询
    * @param: SysOp sysOp
    * @param: @throws Exception :
    * @return: SysOp
    * @throws 
    */
    public SysOp getSysOpByUnameAndPwd(SysOp sysOp) throws Exception;

}