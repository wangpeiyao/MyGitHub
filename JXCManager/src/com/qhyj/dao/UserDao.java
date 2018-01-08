package com.qhyj.dao;

import com.qhyj.domain.UserDo;

public class UserDao extends BaseDao{

	public UserDo getUser(String userName,String passWord){
		UserDo user = new UserDo();
		user.setName("111");
		user.setPass("11");
		user.setUsername("111");
		return user;
	}
}
