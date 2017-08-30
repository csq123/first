package cn.smbms.service.bill;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.pojo.Bill;
@Service
public class BillServiceImpl implements BillService {
    @Resource
    private BillMapper billMapper;
	@Override
	public boolean add(Bill bill) {
		boolean flag=false;
		try{
			if(billMapper.add(bill)>0){
				flag=true;
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public List<Bill> getBillList(String queryproductName, int queryproviderId,
			int queryisPayment, int currentPageNo, int pageSize) {
		List<Bill> billList=new ArrayList<Bill>();
		int pageNo=(currentPageNo-1)*pageSize;
		try{
			billList=billMapper.getBillList(queryproductName,queryproviderId,queryisPayment,pageNo,pageSize);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return billList;
	}

	@Override
	public int getBillCount(String queryproductName, int queryproviderId,
			int queryisPayment) {
		 int count=0;
		  try {
			count=billMapper.getBillCount(queryproductName, queryproviderId, queryisPayment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return count;
	}

	@Override
	public Bill selectProCodeExist(String billCode) {
		Bill bill=null;
		try{
			bill=billMapper.getBill(billCode);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bill;
	}

	@Override
	public boolean deleteBillById(String delId) {
		boolean flag=false;
		try{
			if(billMapper.deleteBillById(delId)>0){
				flag=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public Bill getBillById(String id) {
		Bill bill=null;
		try{
			bill=billMapper.getBillById(id);
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return bill;
	}

	@Override
	public boolean modify(Bill bill) {
		boolean flag=false;
		try{
			if(billMapper.modify(bill)>0){
				flag=true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	
}
