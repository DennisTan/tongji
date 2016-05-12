package com.tongjijinfeng.finance.dao.service;

import com.tongjijinfeng.finance.dao.vo.WeChatAccount;

public interface WeChatDao 
{
	public void insertWeChatUser(WeChatAccount account);

	public WeChatAccount queryUserByOpenId(String openId);
	
}
