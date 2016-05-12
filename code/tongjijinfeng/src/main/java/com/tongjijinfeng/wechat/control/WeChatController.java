package com.tongjijinfeng.wechat.control;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tongjijinfeng.wechat.mp.WXBizMsgCrypt;
import com.tongjijinfeng.wechat.param.OauthAccessToken;
import com.tongjijinfeng.wechat.param.WechatUserInfo;
import com.tongjijinfeng.wechat.service.WeChatService;

@Controller
public class WeChatController {

	private Log log = LogFactory.getLog(WeChatController.class);
	
	@Autowired
	private WeChatService weChatService;
	
	/**
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param bodyData
	 * @return
	 */
	@RequestMapping(value="/wechat/notify")
	public ResponseEntity<?> wechatNotify(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr,@RequestBody String bodyData)
	{
		log.info(" signature : "+signature +" , timestamp : "+timestamp+" , nonce : "+nonce+", echostr "+echostr+" ,  bodyData : "+bodyData);
		WXBizMsgCrypt bizMsgCrypt;
		String result ="";
		try {
			bizMsgCrypt = new WXBizMsgCrypt(WeChatConst.TOKEN, WeChatConst.ENCODINGAESKEY, WeChatConst.APPID);
			boolean res = bizMsgCrypt.verifySignature(signature, timestamp, nonce);
			log.info(" res : "+res);
			if(res)
			{
				result = echostr;
			}
			else
			{
				result = "fail";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping(value="/cardcentre")
	public String cardCentre(@RequestParam String code, @RequestParam String state) {
		
		String result = weChatService.checkCode(code);
		if(StringUtils.isEmpty(result))
		{
			return "cardCentre";
		}
		else
		{
			return result;
		}
	}
	
	/**
	 * 
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping(value="/loancentre")
	public String loanCentre(@RequestParam String code, @RequestParam String state) {
		String result = weChatService.checkCode(code);
		if(StringUtils.isEmpty(result))
		{
			return "loanCentre";
		}
		else
		{
			return result;
		}
	}
	
	/**
	 * 
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping(value="/mentamountmaintcard")
	public String mentionAmountMaintenanceCard(@RequestParam String code, @RequestParam String state)
	{
		String result = weChatService.checkCode(code);
		if(StringUtils.isEmpty(result))
		{
			return "mentionAmountMaintenanceCard";
		}
		else
		{
			return result;
		}
		
	}
}
