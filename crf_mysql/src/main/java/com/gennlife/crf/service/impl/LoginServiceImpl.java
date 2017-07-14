package com.gennlife.crf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gennlife.crf.bean.SysOp;
import com.gennlife.crf.mapper.SysOpMapper;
import com.gennlife.crf.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private SysOpMapper sysOpMapper;
	
	@Override
	public SysOp getSysOpByUnameAndPwd(SysOp sysOp) throws Exception {
		return sysOpMapper.getSysOpByUnameAndPwd(sysOp);
	}

}
