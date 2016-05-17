package com.tongjijinfeng.finance.dao.service;

import java.util.Date;
import java.util.List;

import com.tongjijinfeng.finance.dao.vo.WeChatAccount;

public interface WeChatDao 
{
	public void insertWeChatUser(WeChatAccount account);

	public WeChatAccount queryUserByOpenId(String openId);
	
	public WeChatAccount querySubscribeUserByOpenId(String openId);
	
	public WeChatAccount queryUnsubscribeUserByOpenId(String openId);
	
	public List<WeChatAccount> queryUnsubscribeUser();
	
	public List<WeChatAccount> querySubscribeUser();
	
	public void updateSubscribeStat(String openId, String subscribeStat, String subscribeTime, Date updatetime);
}
