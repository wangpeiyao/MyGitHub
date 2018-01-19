package com.qhyj.dao;

import java.util.List;

import com.qhyj.domain.UserDo;
import com.qhyj.util.AESUtil;

public class UserDao extends BaseDao{

	public UserDo getUser(String userName,String passWord){
//		if("admin".equals(userName)) {
//			UserDo userDo = getUserByName("admin");
//			if(userDo==null) {
//				UserDo initUser = new UserDo();
//				initUser.setUname("admin");
//				initUser.setUserName("����Ա");
//				initUser.setPass("admin");
//				addUser(initUser);
//				return initUser;
//			}else {
//				String password = null;
//				try {
//					password = AESUtil.encrypt(passWord);
//				} catch (Exception e) {
//					e.printStackTrace();
//					throw new RuntimeException("���ܳ���");
//				}
//				if(!password.equals(userDo.getPass())) {
//					throw new RuntimeException("�û����������");
//				}
//				return userDo;
//			}
//		}
		UserDo userDo = getUserByName(userName);
		if(null==userDo&&!"admin".equals(userName)) {
			return null;
		}else if(null==userDo&&"admin".equals(userName)) {
			UserDo initUser = new UserDo();
			initUser.setUname("admin");
			initUser.setUserName("����Ա");
			initUser.setPass("admin");
			addUser(initUser);
			return initUser;

		}
		String password = null;
		try {
			password = AESUtil.encrypt(passWord);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("���ܳ���");
		}
		if(!password.equals(userDo.getPass())) {
			throw new RuntimeException("�û����������");
		}
		return userDo;
	}
	public void addUser(UserDo userDo) {
		int id = super.insert(userDo.getInsertSql());
		userDo.setUid(id);
	}
	public List<UserDo> getAllUserList(){
		return findListBySql(new UserDo(), "SELECT * FROM T_USER");
	}
	
	public void deleteUserByUname(String uname){
		update("DELETE T_USER WHERE uname='"+uname+"'");
	}
	
	public void updateUser(UserDo userDo) {
		try {
			update("UPDATE T_USER SET PASS='"+ AESUtil.encrypt(userDo.getPass())+"' WHERE UNAME='"+userDo.getUname()+"'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("�޸�����ʧ��");
		}
	}
	
	public UserDo getUserByName(String uname) {
		List<UserDo> list =  findListBySql(new UserDo(), "SELECT * FROM T_USER WHERE  UNAME='"+uname+"'");
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
