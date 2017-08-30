package cn.smbms.service.user;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;





import cn.smbms.dao.user.UserMapper;

import cn.smbms.pojo.User;



/**
 * service实现类
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl  implements UserService{
	
	@Resource
	  private UserMapper userMapper;
	/**
	 * 添加
	 */
	public boolean add(User user) {
		boolean flag=false;
		try{
			if(userMapper.add(user)>0){
				flag=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			flag=false;
		}  
		return flag;
	}

	/**
	 * 登陆
	 */
	public User login(String userCode, String userPassword) {
		User user=null;
		try{
			user=userMapper.login(userCode,userPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> getUserList(String queryUserName, int queryUserRole,
			int currentPageNo, int pageSize) {
		List<User> userList=new ArrayList<User>();
		int pageNo=(currentPageNo-1)*pageSize;
		try{
			userList=userMapper.getUserList(queryUserName,queryUserRole,pageNo,pageSize);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public int getUserCount(String queryUserName, int queryUserRole) {
		 int count=0;
		 
		  try {
			
			count=userMapper.getUserCount(queryUserName, queryUserRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return count;
	}

	@Override
	public User selectUserCodeExist(String userCode) {
		User user=null;
		try{
			user=userMapper.getLoginUser(userCode);
		}catch(Exception e) {
			e.printStackTrace();
		}  
		return user;
	}

	@Override
	public boolean deleteUserById(Integer delId) {
		boolean flag=false;
		try{
			if(userMapper.deleteUserById(delId)>0){
				flag=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			flag=false;
		} 
		return flag;
	}

	@Override
	public User getUserById(String id) {
		User user=null;
		try{
			user=userMapper.getUserById(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean modify(User user) {
		boolean flag=false;
		try{
			if(userMapper.modify(user)>0){
				flag=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public boolean updatePwd(int id, String pwd) {
		boolean flag=false;
		try{
			if(userMapper.updatePwd(id, pwd)>0){
				flag=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	
}
