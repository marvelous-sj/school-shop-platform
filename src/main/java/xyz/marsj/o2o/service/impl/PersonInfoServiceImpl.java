package xyz.marsj.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.marsj.o2o.entity.PersonInfo;
import xyz.marsj.o2o.mapper.PersonInfoMapper;
import xyz.marsj.o2o.service.IPersonInfoService;
@Service
public class PersonInfoServiceImpl implements IPersonInfoService {

	@Autowired
	private PersonInfoMapper personInfoMapper;

	@Override
	public PersonInfo getPersonInfoById(long userId) {
		return personInfoMapper.queryPersonInfoById(userId);
	}


}
