package com.tongjijinfeng.startup;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan(basePackages = "com.tongjijinfeng")
@SpringBootApplication
public class TongjijinfengApplication extends WebMvcConfigurerAdapter implements ApplicationContextAware{
	public static ApplicationContext context;
	
	public static void main(String[] args) {
		SpringApplication.run(TongjijinfengApplication.class, args);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		this.context = context;
	}
	
	
}
