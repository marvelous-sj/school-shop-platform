package xyz.marsj.o2o.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import xyz.marsj.o2o.entity.LocalAuth;

public interface LocalAuthMapper {
	//通过用户名和密码查询 用户
	LocalAuth queryLocalByUserNameAndPSW(@Param("userName")String userName,@Param("password")String password);
	//通过userId查询Id
	LocalAuth queryLocalByUserId(@Param("userId")long userId);
	//插入一个本地账户
	int insertLocalAuth(LocalAuth localAuth);
	//更新一个本地账户
	int updateLocalAuth(@Param("userId")long userId,@Param("userName")String userName,@Param("password")String password,
			@Param("newPassword")String newPassword,@Param("lastEditTime")Date lastEditTime);

}
