package com.tongjijinfeng.wechat.service;

import java.util.Map;

import com.tongjijinfeng.wechat.param.OauthAccessToken;
import com.tongjijinfeng.wechat.param.WechatUserInfo;

public interface WeChatService {
	
	public OauthAccessToken acquireOauthAccessToken(String code);
	
	public OauthAccessToken acquireOauthRefreshToken(String refreshToken);
	
	public WechatUserInfo acquireOauthUserInfo(String accessToken, String openId, String lang);
	
	public String checkCode(String code);
	
	public void receiveEvent(Map<String, String> content);
	
	public WechatUserInfo acquireUserInfo(String openId);
}
