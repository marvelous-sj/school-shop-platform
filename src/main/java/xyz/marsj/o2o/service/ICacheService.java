package xyz.marsj.o2o.service;

public interface ICacheService {
	//传入一个前缀key，从redis下删除所有以key开头的缓存
	void removeFromCache(String keyPrefix);
}
