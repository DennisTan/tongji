package com.tongjijinfeng.finance.dao.service;

import com.tongjijinfeng.finance.dao.vo.SysConfig;

public interface CommonDao {
	
	SysConfig querySysConfig(String key);
	
	void insert(String key, String value);
	
	void updateValue(String key, String value);
	
}
