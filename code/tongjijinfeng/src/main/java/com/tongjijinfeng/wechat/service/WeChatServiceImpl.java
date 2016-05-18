package com.tongjijinfeng.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongjijinfeng.finance.common.Const;
import com.tongjijinfeng.finance.common.HttpClientUtils;
import com.tongjijinfeng.finance.dao.service.CommonDao;
import com.tongjijinfeng.finance.dao.service.WeChatDao;
import com.tongjijinfeng.finance.dao.vo.WeChatAccount;
import com.tongjijinfeng.wechat.WeChatGlobalVar;
import com.tongjijinfeng.wechat.control.WeChatConst;
import com.tongjijinfeng.wechat.param.OauthAccessToken;
import com.tongjijinfeng.wechat.param.WechatUserInfo;

@Component
public class WeChatServiceImpl implements WeChatService{

	private Log log = LogFactory.getLog(WeChatServiceImpl.class);
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private WeChatDao weChatDao;
	
	@Override
	public String checkCode(String code)
	{
		//通过code获取鉴权的accesstoken
		OauthAccessToken accessToken = acquireOauthAccessToken(code);
		log.debug("checkCode accessToken :"+accessToken);
		if(accessToken == null)
		{
			return "account_error";
		}
		//通过openid 获取用户数据
		WechatUserInfo userinfo = acquireOauthUserInfo(accessToken.getAccessToken(), accessToken.getOpenId(), "zh_CN");
		log.debug("checkCode userinfo : "+JSONObject.toJSONString(userinfo));
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
	public OauthAccessToken acquireOauthRefreshToken(String refreshToken) 
	{
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
	public WechatUserInfo acquireOauthUserInfo(String accessToken, String openId, String lang) 
	{
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

	/**
	 * 
	 */
	@Override
	public void receiveEvent(Map<String, String> content) {
		if(content.get(WeChatConst.WECHAT_MESSAGE_EVENT_KEY).equalsIgnoreCase(WeChatConst.WECHAT_MESSAGE_EVENT_VALUE_SUBSCRIBE))
		{
			receiveSubscribe(content);
		}
		if(content.get(WeChatConst.WECHAT_MESSAGE_EVENT_KEY).equalsIgnoreCase(WeChatConst.WECHAT_MESSAGE_EVENT_VALUE_UNSUBSCRIBE))
		{
			receiveUnsubscribe(content);
		}
		
	}

	/**
	 * 
	 */
	public WechatUserInfo acquireUserInfo(String openId)
	{
		String url = WeChatConst.WECHATURL+WeChatConst.USERINFOURI;
		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", WeChatGlobalVar.accessToken);
		param.put("openid", openId);
		param.put("lang", "zh_CN");
		String response = HttpClientUtils.doGetWithoutHead(url, param);
		log.debug(" acquireUserInfo response :"+response);
		try {
			JSONObject responseJson = JSONObject.parseObject(response);
			
			if(!responseJson.containsKey("errcode"))
			{
				WechatUserInfo wechatUserInfo = new WechatUserInfo();
				String subscribe = responseJson.getString("subscribe");
				if("0".equalsIgnoreCase(subscribe))
				{
					return null;
				}
				String ret_openId = responseJson.getString("openid");
				wechatUserInfo.setOpenId(ret_openId);
				String ret_nickName = responseJson.getString("nickname");
				wechatUserInfo.setNickname(ret_nickName);
				String ret_sex = responseJson.getString("sex");
				wechatUserInfo.setSex(ret_sex);
				String ret_city = responseJson.getString("city");
				wechatUserInfo.setCity(ret_city);
				String ret_country = responseJson.getString("country");
				wechatUserInfo.setCountry(ret_country);
				String ret_province = responseJson.getString("province");
				wechatUserInfo.setProvince(ret_province);
				String ret_language = responseJson.getString("language");
				
				String ret_headimgurl = responseJson.getString("headimgurl");
				wechatUserInfo.setHeadimgurl(ret_headimgurl);
				String ret_subscribeTime = responseJson.getString("subscribe_time");
				wechatUserInfo.setSubscribeTime(ret_subscribeTime);
				String ret_unionId = responseJson.getString("unionid");
				wechatUserInfo.setUnionid(ret_unionId);
				String ret_remark = responseJson.getString("remark");
				String ret_groupId = responseJson.getString("groupid");
				String ret_tagid_list = responseJson.getString("tagid_list");
				return wechatUserInfo;
			}
			
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param content
	 */
	private void receiveSubscribe(Map<String, String> content)
	{
		log.debug("receiveSubscribe : "+JSONObject.toJSONString(content));
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//判断是否已经在关注人员名单中
				String openId = content.get(WeChatConst.WECHAT_MESSAGE_OPENID_KEY);
				log.debug("openid : "+openId);
				if(!StringUtils.isEmpty(openId))
				{
					WechatUserInfo userInfo = acquireUserInfo(openId);
					WeChatAccount account = weChatDao.queryUserByOpenId(openId);
					if(account == null)
					{
						WeChatAccount newaccount = new WeChatAccount();
						
						if(userInfo != null)
						{
							newaccount.setNickname(userInfo.getNickname());
							newaccount.setCity(userInfo.getCity());
							newaccount.setCountry(userInfo.getCountry());
							newaccount.setCreatetime(new Date());
							newaccount.setHeadimgurl(userInfo.getHeadimgurl());
							newaccount.setOpenid(userInfo.getOpenId());
							newaccount.setPrivilege(userInfo.getPrivilege());
							newaccount.setProvince(userInfo.getProvince());
							newaccount.setSex(userInfo.getSex());
							newaccount.setSubscribeStat(WeChatConst.WECHAT_MESSAGE_EVENT_VALUE_SUBSCRIBE);
							newaccount.setSubscribetime(userInfo.getSubscribeTime());
							newaccount.setUnionid(userInfo.getUnionid());
							newaccount.setUpdatetime(new Date());
							weChatDao.insertWeChatUser(newaccount);
						}
					}
					else
					{
						if(WeChatConst.WECHAT_MESSAGE_EVENT_VALUE_UNSUBSCRIBE.equalsIgnoreCase(account.getSubscribeStat()))
						{
							if(userInfo != null)
							{
								weChatDao.updateSubscribeStat(openId, WeChatConst.WECHAT_MESSAGE_EVENT_VALUE_SUBSCRIBE, userInfo.getSubscribeTime(), new Date());
							}
							
				 		}
					}
					
				}
			}
		});
		
		thread.start();
	}
	
	private void receiveUnsubscribe(Map<String, String> content)
	{
		log.debug("receiveUnsubscribe : "+JSONObject.toJSONString(content));
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//判断是否已经在关注人员名单中
				String openId = content.get(WeChatConst.WECHAT_MESSAGE_OPENID_KEY);
				log.debug("openid : "+openId);
				if(!StringUtils.isEmpty(openId))
				{
					WeChatAccount account = weChatDao.queryUserByOpenId(openId);
					if(account != null)
					{
						weChatDao.updateSubscribeStat(openId, WeChatConst.WECHAT_MESSAGE_EVENT_VALUE_UNSUBSCRIBE, account.getSubscribetime(), new Date());
					}
				}
			}
		});
		thread.start();
	}

	@Override
	public List<String> acquireUserList() {
		// TODO Auto-generated method stub
		String url = WeChatConst.WECHATURL+WeChatConst.GETUSERLISTURI;
		boolean flag = true;
		String next_openid = null;
		List<String> openids = new ArrayList<String>(); 
		while (flag) 
		{
			Map<String, String> param = new HashMap<String, String>();
			param.put("access_token",WeChatGlobalVar.accessToken);
			if(next_openid != null)
			{
				param.put("next_openid", next_openid);
			}
			String response = HttpClientUtils.doGetWithoutHead(url, param);
			log.debug(" acquireUserList response :"+response);
			try {
				JSONObject responseJson = JSONObject.parseObject(response);
				if(!responseJson.containsKey("errcode"))
				{
					JSONObject data = responseJson.getJSONObject("data");
					if(data != null)
					{
						JSONArray openidArray = data.getJSONArray("openid");
						if(openidArray != null)
						{
							for (int i = 0; i < openidArray.size(); i++) 
							{
								openids.add(openidArray.getString(i));
							}
						}
					}
					String nextopenid = responseJson.getString("next_openid");
					if(StringUtils.isEmpty(nextopenid))
					{
						flag = false;
					}
					next_openid = nextopenid;
				}
				else
				{
					flag = false;
				}
			} catch (Exception e) {
				log.error(e);
				flag = false;
			}
		}
		return openids;
	}

	@Override
	public void setMenu() {
		String menuCommit = commonDao.querySysConfig(Const.SYS_CONFIG_KEY_NEMUCOMMIT).getValue();
		
		if(Const.SYS_CONFIG_VALUE_NEMUCOMMIT_FALSE.equals(menuCommit))
		{
			try {
				// TODO Auto-generated method stub
				String url = WeChatConst.WECHATURL+WeChatConst.MENUCREATEURI+"?access_token="+WeChatGlobalVar.accessToken;
				String menu = commonDao.querySysConfig(Const.SYS_CONFIG_KEY_MENU).getValue();
				if(!StringUtils.isEmpty(menu))
				{
					String response = HttpClientUtils.doPost(url, menu);
					log.info("setMenu reponse : "+response);
					JSONObject responseJson = JSONObject.parseObject(response);
					int errCode = responseJson.getIntValue("errcode");
					if(errCode == 0)
					{
						commonDao.updateValue(Const.SYS_CONFIG_KEY_NEMUCOMMIT, Const.SYS_CONFIG_VALUE_NEMUCOMMIT_TRUE);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void getUsers() {
		String usersrefreshed = commonDao.querySysConfig(Const.SYS_CONFIG_KEY_USERSREFRESH).getValue();
		if(Const.SYS_CONFIG_VALUE_USERSREFRESH_FALSE.equals(usersrefreshed))
		{
			List<String> openids = acquireUserList();
			for(int i = 0; i < openids.size(); i++) 
			{
				String openid = openids.get(i);
				WechatUserInfo userInfo = acquireUserInfo(openid);
				WeChatAccount account = weChatDao.queryUserByOpenId(openid);
				if(account == null)
				{
					WeChatAccount newaccount = new WeChatAccount();
					
					if(userInfo != null)
					{
						newaccount.setNickname(userInfo.getNickname());
						newaccount.setCity(userInfo.getCity());
						newaccount.setCountry(userInfo.getCountry());
						newaccount.setCreatetime(new Date());
						newaccount.setHeadimgurl(userInfo.getHeadimgurl());
						newaccount.setOpenid(userInfo.getOpenId());
						newaccount.setPrivilege(userInfo.getPrivilege());
						newaccount.setProvince(userInfo.getProvince());
						newaccount.setSex(userInfo.getSex());
						newaccount.setSubscribeStat(WeChatConst.WECHAT_MESSAGE_EVENT_VALUE_SUBSCRIBE);
						newaccount.setSubscribetime(userInfo.getSubscribeTime());
						newaccount.setUnionid(userInfo.getUnionid());
						newaccount.setUpdatetime(new Date());
						weChatDao.insertWeChatUser(newaccount);
					}
				}
				else
				{
					if(userInfo != null)
					{
						weChatDao.updateSubscribeStat(openid, WeChatConst.WECHAT_MESSAGE_EVENT_VALUE_SUBSCRIBE, userInfo.getSubscribeTime(), new Date());
					}
				}
			}
			commonDao.updateValue(Const.SYS_CONFIG_KEY_USERSREFRESH, Const.SYS_CONFIG_VALUE_USERSREFRESH_TRUE);
		}
	}

	@Override
	public List<WechatUserInfo> acquireUserInfoBatch(List<String> openIdlist) {
		// TODO Auto-generated method stub
		return null;
	}
}
