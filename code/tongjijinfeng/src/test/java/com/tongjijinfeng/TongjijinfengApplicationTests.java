package com.tongjijinfeng;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tongjijinfeng.startup.TongjijinfengApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TongjijinfengApplication.class)
@WebAppConfiguration
public class TongjijinfengApplicationTests {

	@Test
	public void contextLoads() {
	}

}
