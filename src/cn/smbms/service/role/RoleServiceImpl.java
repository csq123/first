package cn.smbms.service.role;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;


import cn.smbms.dao.role.RoleMapper;
import cn.smbms.dao.user.UserMapper;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;

@SuppressWarnings("all")
@Service
public class RoleServiceImpl implements RoleService {
     @Resource
      private RoleMapper roleMapper;
	@Override
	public List<Role> getRoleList() {
		
		List<Role> list=new ArrayList<Role>();
		try{
		
		list=roleMapper.getRoleList();
	}catch(Exception e) {
		e.printStackTrace();
	}
		return list;
	}
	

}
