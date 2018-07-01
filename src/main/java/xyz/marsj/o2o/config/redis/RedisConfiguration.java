package xyz.marsj.o2o.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPoolConfig;
import xyz.marsj.o2o.cache.JedisPoolWriper;
import xyz.marsj.o2o.cache.JedisUtil;

@Configuration
public class RedisConfiguration {
	@Value("${redis.hostname}")
	private String hostname;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.database}")
	private int dataBase;
	@Value("${redis.pool.maxActive}")
	private int maxActive;
	@Value("${redis.pool.maxIdle}")
	private int maxIdle;
	@Value("${redis.pool.maxWait}")
	private long maxWait;
	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;
	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	@Autowired
	private JedisPoolWriper jedisWritePool;
	@Autowired
	private JedisUtil jedisUtil;	
	@Bean(name="jedisPoolConfig")
	public JedisPoolConfig createJedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		//可分配多少个redis实例
		jedisPoolConfig.setMaxTotal(maxActive);
		//最大空闲连接数
		jedisPoolConfig.setMaxIdle(maxIdle);
		//最大等待时间
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		//获取连接是是否检查有效性
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		return jedisPoolConfig;
	}
	@Bean(name="jedisWritePool")
	public JedisPoolWriper createJedisPoolWriper() {
		 JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig, hostname, port);
		 return jedisPoolWriper;
	}
	@Bean(name="jedisUtil")
	public JedisUtil createJedisUtil() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setJedisPool(jedisWritePool);
		return jedisUtil;
	}
	@Bean(name="jedisKeys")
	public JedisUtil.Keys createJedisKeys() {
		JedisUtil.Keys jedisKeys = jedisUtil.new Keys();
		return jedisKeys;
	}
	@Bean(name="jedisStrings")
	public JedisUtil.Strings createJedisStrings() {
		JedisUtil.Strings jedisStrings = jedisUtil.new Strings();
		return jedisStrings;
	}
	@Bean(name="jedisLists")
	public JedisUtil.Lists createJedisLists() {
		JedisUtil.Lists jedisLists = jedisUtil.new Lists();
		return jedisLists;
	}
	@Bean(name="jedisSets")
	public JedisUtil.Sets createJedisSets() {
		JedisUtil.Sets jedisSets = jedisUtil.new Sets();
		return jedisSets;
	}
	@Bean(name="jedisHash")
	public JedisUtil.Hash createJedisHash() {
		JedisUtil.Hash jedisHash = jedisUtil.new Hash();
		return jedisHash;
	}
	
}
