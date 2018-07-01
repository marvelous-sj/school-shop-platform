package xyz.marsj.o2o.service;

import xyz.marsj.o2o.dto.UserProductMapExecution;
import xyz.marsj.o2o.entity.UserProductMap;
import xyz.marsj.o2o.exceptions.UserProductMapOperationException;

public interface IUserProductMapService {
	UserProductMapExecution listUserProductMap(UserProductMap userProductMapCondition ,Integer pageIndex,Integer pageSize);
	UserProductMapExecution addUserProductMap(UserProductMap userProductMapCondition) throws UserProductMapOperationException;
}
