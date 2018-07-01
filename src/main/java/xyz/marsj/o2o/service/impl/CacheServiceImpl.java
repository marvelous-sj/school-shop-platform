package xyz.marsj.o2o.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.marsj.o2o.cache.JedisUtil;
import xyz.marsj.o2o.service.ICacheService;
@Service
public class CacheServiceImpl implements ICacheService {
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Override
	public void removeFromCache(String keyPrefix) {
		Set<String> keys = jedisKeys.keys(keyPrefix+"*");
		
		for (String string : keys) {
			jedisKeys.del(string);
		}
		
	}

}
