package com.tongjijinfeng.web.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController 
{
	private Log log = LogFactory.getLog(WebController.class);
	
	@RequestMapping(value="/demo")
	public String demo()
	{
		return "demo";
	}
	
	@RequestMapping(value="/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="/cardcentre/handlecardpre")
	public String handleCardPrefecture() {
		return "handlecardPrefecture";
	}
	
	@RequestMapping(value="/cardcentre/handlecardpre/kuaikatongdao")
	public String quickCardChannel() {
		return "kuaikatongdao";
	}
	
	@RequestMapping(value="/cardcentre/applycardcondition")
	public String applyCardCondition() {
		return "applyCardCondition";
	}
	
	@RequestMapping(value="/cardcentre/handlecardstrategy")
	public String HandleCardStrategy() {
		return "handleCardStrategy";
	}
	
	@RequestMapping(value="/cardcentre/whiteuserstrategy")
	public String whiteUserStrategy() {
		return "whiteUserStrategy";
	}
	
	
	@RequestMapping(value="/loancentre/p2pnetloan")
	public String p2pNetLoan() {
		return "p2pwangdai";
	}
	
	@RequestMapping(value="/loancentre/chuqiankouzi")
	public String chuQianKouZi() {
		return "chuqiankouzi";
	}
	
	@RequestMapping(value="/mentamountmaintcard/mentamount")
	public String mentionAmount()
	{
		return "mentionAmount";
	}
	
	@RequestMapping(value="/mentamountmaintcard/mentamount/soft")
	public String mentionAmountSoft()
	{
		return "mentionAmountsoft";
	}
	
	@RequestMapping(value="/mentamountmaintcard/mentamount/helper")
	public String mentionAmountHelper()
	{
		return "mentionAmounthelper";
	}
	
	@RequestMapping(value="/mentamountmaintcard/mentamount/helper/doc")
	public String mentionAmountHelperDoc(@RequestParam String id)
	{
		return "mentionAmountHelperDoc";
	}
	
	@RequestMapping(value="/mentamountmaintcard/maintenanceCard")
	public String maintenanceCard()
	{
		return "maintenanceCard";
	}
	
	@RequestMapping(value="/account/error")
	public String accountError()
	{
		return "account_error";
	}
}
