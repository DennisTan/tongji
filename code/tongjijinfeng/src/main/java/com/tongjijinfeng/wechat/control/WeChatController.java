package com.tongjijinfeng.wechat.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




import com.alibaba.fastjson.JSONObject;
import com.tongjijinfeng.wechat.mp.WXBizMsgCrypt;
import com.tongjijinfeng.wechat.mp.XMLParse;
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
	@RequestMapping(value={"/wechat/notify"}, method = RequestMethod.GET)
	public ResponseEntity<?> wechatNotifyGet(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr)
	{
		log.info(" wechatNotifyGet signature : "+signature +" , timestamp : "+timestamp+" , nonce : "+nonce+", echostr "+echostr);
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
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/wechat/notify"}, method = RequestMethod.POST)
	public ResponseEntity<?> wechatNotifyPost(HttpServletRequest request)
	{
		log.debug("wechatNotifyPost UIR : "+request.getRequestURI());
		log.debug("wechatNotifyPost param : "+JSONObject.toJSONString(request.getParameterMap()));
		log.debug("wechatNotifyPost Attribute : "+JSONObject.toJSONString(request.getAttributeNames()));
		log.debug("wechatNotifyPost CharacterEncoding : "+JSONObject.toJSONString(request.getCharacterEncoding()));
		log.debug("wechatNotifyPost Cookies : "+JSONObject.toJSONString(request.getCookies()));
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			while((line = br.readLine()) != null)
			{
				sb.append(line);
			}
		} catch (IOException e) {
			log.error(e);
		}
		log.debug("wechatNotifyPost body : "+sb.toString());
		String xmlcontent = sb.toString();
		
		if(!StringUtils.isEmpty(xmlcontent))
		{
			Map<String, String> element = XMLParse.parseMessageXML(xmlcontent);
			log.debug("wechatNotifyPost parseMessageXML : "+ JSONObject.toJSONString(element));
			if(element.get(WeChatConst.WECHAT_MESSAGE_MSGTYPE_KEY).equalsIgnoreCase(WeChatConst.WECHAT_MESSAGE_MSGTYPE_VALUE_EVENT))
			{
				weChatService.receiveEvent(element);
			}
			
		}
		return ResponseEntity.ok("");
	}
	
	/**
	 * 
	 * @param code
	 * @param state
	 * @return
	 */
	@RequestMapping(value="/cardcentre")
	public String cardCentre(@RequestParam String code, @RequestParam String state) {
		
		log.debug("code : "+code+", state : "+state);
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
		log.debug("code : "+code+", state : "+state);
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
		log.debug("code : "+code+", state : "+state);
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
	
	@RequestMapping(value="/my/centre")
	public String mycentre(@RequestParam String code, @RequestParam String state) {
		log.debug("code : "+code+", state : "+state);
		String result = weChatService.checkCode(code);
		if(StringUtils.isEmpty(result))
		{
			return "myCentre";
		}
		else
		{
			return result;
		}
		
	}
	
	@RequestMapping(value="/setmenu")
	public ResponseEntity<?> setMenu(@RequestParam String pass)
	{
		if("tanxian".equals(pass))
		{
			weChatService.setMenu();
			return ResponseEntity.ok("finish");
		}
		else
		{
			return ResponseEntity.ok("pass is error");
		}
		
	}
	
	@RequestMapping(value="/getUsers")
	public ResponseEntity<?> getUsers(@RequestParam String pass)
	{
		if("tanxian".equals(pass))
		{
			weChatService.getUsers();
			return ResponseEntity.ok("finish");
		}
		else
		{
			return ResponseEntity.ok("pass is error");
		}
		
	}
}
