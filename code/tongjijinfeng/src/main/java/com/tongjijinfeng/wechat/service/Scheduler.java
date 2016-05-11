package com.tongjijinfeng.wechat.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.tongjijinfeng.finance.common.Const;
import com.tongjijinfeng.finance.common.HttpClientUtils;
import com.tongjijinfeng.finance.dao.service.CommonDao;
import com.tongjijinfeng.wechat.WeChatGlobalVar;
import com.tongjijinfeng.wechat.control.WeChatConst;

@Component
public class Scheduler {

	private Log log = LogFactory.getLog(Scheduler.class);
	
	@Autowired
	private CommonDao commonDao;

	@Scheduled(cron = "0/7200 * * * * ?")
	public void checkAccessToken()
	{
		log.debug("start checkAccessToken : ");
		String url = WeChatConst.WECHATURL+WeChatConst.TOKENURI;
		Map<String, String> keyValueMap = new HashMap<String,String>();
		keyValueMap.put("grant_type", "client_credential");
		keyValueMap.put("appid", WeChatConst.APPID);
		keyValueMap.put("secret", WeChatConst.APPSECRET);
		log.debug(" checkAccessToken : "+url);
		String response = HttpClientUtils.doGetWithoutHead(url, keyValueMap);
		log.debug("checkAccessToken, response : "+response);
		try {
			JSONObject responsejson = JSONObject.parseObject(response);
			String accessToken = responsejson.getString("access_token");
			int expiresIn = responsejson.getIntValue("expires_in");
			log.info("checkAccessToken accessToken : "+accessToken+", expiresIn : "+expiresIn);
			WeChatGlobalVar.accessToken = accessToken;
			commonDao.updateValue(Const.SYS_CONFIG_KEY_ACCESSTOKEN, accessToken);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		setMenu();
	}
	
	/**
	 * 
	 */
	private void setMenu()
	{
		try {
			
			if(WeChatGlobalVar.accessToken == null)
			{
				log.info(" access token is null, thread sleep 1 min ");
				Thread.sleep(1000*60);
			}
			// TODO Auto-generated method stub
			String url = WeChatConst.WECHATURL+WeChatConst.MENUCREATE+"?access_token="+WeChatGlobalVar.accessToken;
			String menu = commonDao.querySysConfig(Const.SYS_CONFIG_KEY_MENU).getValue();
			String menuCommit = commonDao.querySysConfig(Const.SYS_CONFIG_KEY_NEMUCOMMIT).getValue();
			if(!StringUtils.isEmpty(menu) && Const.SYS_CONFIG_VALUE_NEMUCOMMIT_FALSE.equals(menuCommit))
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
			// TODO: handle exception
		}
	}
	
}
