package cn.smbms.service.bill;

import java.util.List;

import cn.smbms.pojo.Bill;




public interface BillService {
	/**
	 * 添加订单信息
	 * @param bill
	 * @return
	 */
	public boolean add(Bill bill);
	/**
	 * 根据条件查询订单信息列表
	 * @param queryBillcode
	 * @param queryProname
	 * @param queryisPayment
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public List<Bill> getBillList(String queryproductName,int queryproviderId,int queryisPayment, int currentPageNo, int pageSize);
	/**
	 * 根据条件查询总记录数
	 * @param queryproductName
	 * @param queryProname
	 * @param queryisPayment
	 * @return
	 */
	public int getBillCount(String queryproductName,int queryproviderId,int queryisPayment);
	/**
	 * 根据 billCode查出Bill
	 * @param billCode
	 * @return
	 */
	public Bill selectProCodeExist(String billCode);
	/**
	 * 根据ID删除Bill
	 * @param delId
	 * @return
	 */
	public boolean deleteBillById(String delId);
	/**
	 * 根据ID查找Bill
	 * @param id
	 * @return
	 */
	public Bill getBillById(String id);
	/**
	 * 修改订单信息
	 * @param bill
	 * @return
	 */
	public boolean modify(Bill bill);
	
}
