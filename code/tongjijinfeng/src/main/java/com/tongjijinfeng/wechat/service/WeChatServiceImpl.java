package com.tongjijinfeng.wechat.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.tongjijinfeng.finance.common.HttpClientUtils;
import com.tongjijinfeng.finance.dao.service.CommonDao;
import com.tongjijinfeng.wechat.control.WeChatConst;
import com.tongjijinfeng.wechat.param.OauthAccessToken;
import com.tongjijinfeng.wechat.param.WechatUserInfo;

@Component
@Transactional
public class WeChatServiceImpl implements WeChatService{

	private Log log = LogFactory.getLog(WeChatServiceImpl.class);
	
	@Autowired
	private CommonDao commonDao;
	
	@Override
	public String checkCode(String code)
	{
		//通过code获取鉴权的accesstoken
		OauthAccessToken accessToken = acquireOauthAccessToken(code);
		if(accessToken == null)
		{
			return "account_error";
		}
		//通过openid 获取用户数据
		WechatUserInfo userinfo = acquireWechatUserInfo(accessToken.getAccessToken(), accessToken.getOpenId(), "zh_CN");
		if(userinfo == null)
		{
			return "account_error";
		}
		return null;
	}
	
	@Override
	public OauthAccessToken acquireOauthAccessToken(String code)
	{
		String url = WeChatConst.WECHATOAUTHURL+WeChatConst.OAUTHACCESSTOKENURI;
		Map<String, String> param = new HashMap<String, String>();
		param.put("appid", WeChatConst.APPID);
		param.put("secret", WeChatConst.APPSECRET);
		param.put("code", code);
		param.put("grant_type", "authorization_code");
		String response = HttpClientUtils.doGetWithoutHead(url, param);
		log.debug(" acquireOauthAccessToken response :"+response);
		try {
			
			JSONObject responseJson = JSONObject.parseObject(response);
			OauthAccessToken authAccessToken = new OauthAccessToken();
			if(!responseJson.containsKey("errcode"))
			{
				authAccessToken.setAccessToken(responseJson.getString("access_token"));
				authAccessToken.setExpiresIn(responseJson.getString("exoires_in"));
				authAccessToken.setOpenId(responseJson.getString("openid"));
				authAccessToken.setScope(responseJson.getString("scope"));
				return authAccessToken;
			}
			return null;
		} catch (Exception e) {
			log.error(e);
			
		}
		return null;
	}

	@Override
	public OauthAccessToken acquireOauthRefreshToken(String refreshToken) {
		// TODO Auto-generated method stub
		
		String url =  WeChatConst.WECHATOAUTHURL+WeChatConst.OAUTHREFRESHTOKENURI;
		Map<String, String> param = new HashMap<String, String>();
		param.put("appid", WeChatConst.APPID);
		param.put("grant_type", "refresh_token");
		param.put("refresh_token", refreshToken);
		String response = HttpClientUtils.doGetWithoutHead(url, param);
		log.debug(" acquireOauthRefreshToken response :"+response);
		try {
			JSONObject responseJson = JSONObject.parseObject(response);
			OauthAccessToken authAccessToken = new OauthAccessToken();
			if(!responseJson.containsKey("errcode"))
			{
				authAccessToken.setAccessToken(responseJson.getString("access_token"));
				authAccessToken.setExpiresIn(responseJson.getString("exoires_in"));
				authAccessToken.setOpenId(responseJson.getString("openid"));
				authAccessToken.setScope(responseJson.getString("scope"));
				return authAccessToken;
			}
			return null;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public WechatUserInfo acquireWechatUserInfo(String accessToken, String openId, String lang) {
		// TODO Auto-generated method stub
		String url =  WeChatConst.WECHATOAUTHURL+WeChatConst.OAUTHUSERINFOURI;
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token",accessToken);
		param.put("openid", openId);
		param.put("lang", lang);
		String response = HttpClientUtils.doGetWithoutHead(url, param);
		log.debug(" acquireWechatUserInfo response :"+response);
		try {
			JSONObject responseJson = JSONObject.parseObject(response);
			WechatUserInfo wechatUserInfo = new WechatUserInfo();
			if(!responseJson.containsKey("errcode"))
			{
				wechatUserInfo.setOpenId(responseJson.getString("openid"));
				wechatUserInfo.setNickname(responseJson.getString("nickname"));
				wechatUserInfo.setSex(responseJson.getString("sex"));
				wechatUserInfo.setProvince(responseJson.getString("province"));
				wechatUserInfo.setCity(responseJson.getString("city"));
				wechatUserInfo.setCountry(responseJson.getString("country"));
				wechatUserInfo.setHeadimgurl(responseJson.getString("headimgurl"));
				wechatUserInfo.setPrivilege(responseJson.getString("privilege"));
				wechatUserInfo.setUnionid(responseJson.getString("unionid"));
				return wechatUserInfo;
			}
			return null;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

}
