package xyz.marsj.o2o.service;

import xyz.marsj.o2o.dto.UserAwardMapExecution;
import xyz.marsj.o2o.entity.UserAwardMap;

public interface IUserAwardMapService {
	UserAwardMapExecution getUserAwardMapList(UserAwardMap userAwardMapCondition ,int pageIndex,int pageSize);
	UserAwardMap getUserAwardMapById(long userAwardMapId);
	UserAwardMap getUserAwardMapByIdFrontend(long userAwardMapId);
	UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap);
	UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardMapCondition ,Integer pageIndex,Integer pageSize);
	UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap);
}
