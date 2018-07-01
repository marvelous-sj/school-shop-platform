package xyz.marsj.o2o.config.dao;

import java.beans.PropertyVetoException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

import xyz.marsj.o2o.util.DESUtils;

@Configuration
@MapperScan("xyz.marsj.o2o.mapper")
public class DateSourceConfiguration {
	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	/**
	 * 原本使用c3p0 但是一直给我报java.lang.AbstractMethodError: Method com/mchange/v2/c3p0/impl/NewProxyPreparedStatement.isClosed()Z is abstract
	 * 错，没办法只好换成Druid了，用了druid就不报错了
	 * @return
	 * @throws PropertyVetoException
	 */
	@Bean(name="dataSource")
	public DruidDataSource createDataSource() throws PropertyVetoException  {
		//生成dataSource实例
		DruidDataSource dataSource =new DruidDataSource();
		dataSource.setDriverClassName(jdbcDriver);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(DESUtils.getDecryptString(jdbcUsername));
		dataSource.setPassword(DESUtils.getDecryptString(jdbcPassword));
		dataSource.setMaxActive(30);
		dataSource.setMinIdle(10);
		dataSource.setMaxWait(10000);
		return dataSource;
	}
	
}
