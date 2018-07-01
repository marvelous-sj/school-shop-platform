package xyz.marsj.o2o.mapper;

import xyz.marsj.o2o.entity.PersonInfo;

public interface PersonInfoMapper {
	PersonInfo queryPersonInfoById(long userId);
	int insertPersonInfo(PersonInfo personInfo);
}
