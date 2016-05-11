package com.tongjijinfeng.finance.dao.service;

import com.tongjijinfeng.finance.dao.vo.SysConfig;

public interface CommonDao {
	
	SysConfig querySysConfig(String cof_key);
	
	void insert(String cof_key, String cof_value);
	
	void updateValue(String cof_key, String cof_value);
	
	
}
