package com.tongjijinfeng;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tongjijinfeng.finance.dao.service.WeChatDao;
import com.tongjijinfeng.startup.TongjijinfengApplication;
import com.tongjijinfeng.wechat.param.WechatUserInfo;
import com.tongjijinfeng.finance.dao.vo.WeChatAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TongjijinfengApplication.class)
@WebAppConfiguration
public class WeChatTest {
    
	@Autowired
    private WeChatDao wechatDao;
	
//	@Test
//	public void weChatDaoInsertWeChatUser()
//	{
//		WeChatAccount account = new WeChatAccount();
//		account.setOpenid("openidtest");
//		account.setCity("guangzhou");
//		account.setCountry("zhongguo");
//		account.setHeadimgurl("test");
//		account.setNickname("nickname");
//		account.setPrivilege("privilege");
//		account.setUnionid("unionid");
//		account.setSex("1");
//		account.setProvince("guangdong");
//		account.setCreatetime(new Date());
//		account.setUpdatetime(new Date());
//		wechatDao.insertWeChatUser(account);
//		assertThat(1, is(1));
//	}
	
//	@Test
//	public void weChatDaoQueryUserByOpenId()
//	{
//		WeChatAccount account = wechatDao.queryUserByOpenId("openidtest");
//		assertThat("openidtest", is(account.getOpenid()));
//	}
}
