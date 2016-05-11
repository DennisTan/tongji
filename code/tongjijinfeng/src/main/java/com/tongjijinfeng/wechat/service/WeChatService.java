package com.tongjijinfeng.wechat.service;

import com.tongjijinfeng.wechat.param.OauthAccessToken;
import com.tongjijinfeng.wechat.param.WechatUserInfo;

public interface WeChatService {
	public OauthAccessToken acquireOauthAccessToken(String code);
	
	public OauthAccessToken acquireOauthRefreshToken(String refreshToken);
	
	public WechatUserInfo acquireWechatUserInfo(String accessToken, String openId, String lang);
	
}
