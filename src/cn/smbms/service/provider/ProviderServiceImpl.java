package cn.smbms.service.provider;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;


import cn.smbms.dao.bill.BillMapper;
import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.dao.role.RoleMapper;
import cn.smbms.dao.user.UserMapper;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.user.UserServiceImpl;


@SuppressWarnings("all")
@Service
public class ProviderServiceImpl implements ProviderService {
	@Resource
	private ProviderMapper providerMapper;
	@Resource
	private BillMapper billMapper;
	@Override
	public boolean add(Provider provider) {
		boolean flag=false;
		try{
			if(providerMapper.add(provider)>0){
				flag=true;
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			
		} 
		return flag;
	}

	@Override
	public List<Provider> getProList(String queryProcode, String queryProname,
			int currentPageNo, int pageSize) {
		List<Provider> providerList=new ArrayList<Provider>();
		int pageNo=(currentPageNo-1)*pageSize;
		try{
			providerList=providerMapper.getProList(queryProcode,queryProname,pageNo,pageSize);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return providerList;
	}

	@Override
	public int getProviderCount(String queryProcode, String queryProname) {
		int count=0;
		  try {
			
			count=providerMapper.getProCount(queryProcode, queryProname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return count;
	}

	@Override
	public Provider selectProCodeExist(String proCode) {
		Provider provider=null;
		try{
			provider=providerMapper.getpro(proCode);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return provider;
	}

	@Override
	public int deleteProviderById(String delId) {
		int billCount = -1;
		try{
			billCount = billMapper.getBillCountByProviderId(delId);
			if(billCount==0){
				providerMapper.deleteProById(delId);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return billCount;
	}

	@Override
	public Provider getProviderById(String id) {
		Provider provider=null;
		try{
			provider=providerMapper.getProById(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return provider;
		
	}

	@Override
	public boolean modify(Provider provider) {
		
		boolean flag=false;
		try{
			
			if(providerMapper.modify(provider)>0){
				flag=true;
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public List<Provider> getProList() {
		List<Provider> list=new ArrayList<Provider>();
		try{
		list=providerMapper.getproList();
	}catch(Exception e) {
		e.printStackTrace();
	}
		return list;
	}
	
}
