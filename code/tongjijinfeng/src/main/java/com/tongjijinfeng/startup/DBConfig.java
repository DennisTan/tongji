package com.tongjijinfeng.startup;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({ com.tongjijinfeng.startup.DBConfig.DBConf.class })
public class DBConfig  implements ApplicationContextAware{

	private Log log = LogFactory.getLog(DBConfig.class);
	private ApplicationContext ctx;

	@Bean(name = "mysql_datasource")
	@Primary
	DataSource mysql_datasource(DBConf dbConf) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(dbConf.getDriverClassName());
		
		dataSource.setUrl(dbConf.getUrl());
		dataSource.setUsername(dbConf.getUser());
		dataSource.setPassword(dbConf.getPassword());
		dataSource.setMinIdle(dbConf.getMinIdle());
		dataSource.setMinIdle(dbConf.getMaxIdle());
		dataSource.setMaxActive(dbConf.getMaxActive());
		log.info("init mysql_datasource success");
		return dataSource;
	}

	@Bean(name = "mysql_sqlsession")
	@DependsOn("mysql_datasource")
	SqlSessionFactoryBean sqlSessionFactory() throws IOException {
		SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
		sqlSession.setDataSource(ctx.getBean("mysql_datasource", DataSource.class));
		sqlSession.setTypeAliasesPackage("com.tongjijinfeng.finance.dao.model");
		sqlSession.setConfigLocation(new ClassPathResource("com/tongjijinfeng/finance/dao/mapper/mybatis_config.xml"));
		Resource[] resources = new PathMatchingResourcePatternResolver()
				.getResources("classpath:com/tongjijinfeng/finance/dao/mapper/*mapper.xml");
		sqlSession.setMapperLocations(resources);
		return sqlSession;
	}

	@Bean(name = "mysql_transactionmanager")
	@DependsOn("mysql_datasource")
	PlatformTransactionManager sdkserver_transactionManager() {
		return new DataSourceTransactionManager(ctx.getBean("mysql_datasource", DataSource.class));
	}

	@Bean(name = "mysql_mapper")
	MapperScannerConfigurer mapperScannerConfigure() {
		MapperScannerConfigurer scanner = new MapperScannerConfigurer();
		scanner.setBasePackage("com.tongjijinfeng.finance.dao.service");
		scanner.setSqlSessionFactoryBeanName("mysql_sqlsession");
		return scanner;
	}
	
	@Bean(name = "mysql_jdbc")
	@DependsOn("mysql_datasource")
	public JdbcTemplate mysql_jdbcTemplate(DataSource mysql_datasource) {
		return new JdbcTemplate(mysql_datasource);
	}

	@ConfigurationProperties(prefix = "db")
	public static class DBConf {
		private String driverClassName;
		private String url;
		private String ip;
		private int port;
		private String user;
		private String password;
		private String sid;
		private int minIdle;
		private int maxIdle;
		private int maxActive;

		public String getSid() {
			return sid;
		}

		public void setSid(String sid) {
			this.sid = sid;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public int getMaxIdle() {
			return maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}
		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}
}
