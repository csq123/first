package cn.smbms.dao.bill;

import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Bill;



/**
 * 供应商管理
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public interface BillMapper {
	/**
	 * 添加订单信息
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	public int add(Bill bill)throws Exception;

	/**
	 * 通过条件查询BillList
	 * @param connection
	 * @param productName
	 * @param ProName
	 * @param isPayment
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Bill> getBillList(@Param("productName")String productName,
			@Param("providerId")int providerId,
			@Param("isPayment")int isPayment, 
			@Param("currentPageNo")int currentPageNo, 
			@Param("pageSize")int pageSize)throws Exception;
	/**
	 * 条件查询，订单信息表记录总数
	 * @param connection
	 * @param productName
	 * @param ProName
	 * @param isPayment
	 * @return
	 * @throws Exception
	 */
	public int getBillCount(@Param("productName")String productName,
			@Param("providerId")int providerId,
			@Param("isPayment")int isPayment)throws Exception;
	/**
	 * 删除订单信息
	 * @param connection
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public int deleteBillById(@Param("id")String delId)throws Exception; 
	/**
	 * 查看订单信息
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Bill getBillById(@Param("id")String id)throws Exception; 
	
	/**
	 * 修改供应商信息
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	public int modify(Bill bill)throws Exception;
	/**
	 * 通过billCode查找
	 * @param connection
	 * @param billCode
	 * @return
	 * @throws Exception
	 */
	public Bill getBill(@Param("billCode")String billCode)throws Exception;
	
	/**
	 * 根据供应商ID查询订单数量
	 * @param connection
	 * @param providerId
	 * @return
	 * @throws Exception
	 */
	public int getBillCountByProviderId(@Param("providerIds")String providerIds)throws Exception;
	
}
