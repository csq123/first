package cn.smbms.dao.provider;

import java.sql.Connection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Provider;



/**
 * 供应商管理
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public interface ProviderMapper {
	/**
	 * 增加用户信息
	 * @param connection
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public int add(Provider provider)throws Exception;

	/**
	 * 通过条件查询-ProList
	 * @param connection
	 * @param ProName
	 * @return
	 * @throws Exception
	 */
	public List<Provider> getProList(@Param("proCode")String proCode,@Param("proName")String proName, 
			                         @Param("currentPageNo")int currentPageNo, @Param("pageSize")int pageSize)throws Exception;
	/**
	 * 条件查询,用户表记录数
	 * @param connection
	 * @param ProName
	 * @return
	 * @throws Exception
	 */
	public int getProCount(@Param("proCode")String proCode,@Param("proName")String proName)throws Exception;
	
	/**
	 * 删除供应商信息
	 * @param connection
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public int deleteProById(@Param("id")String id)throws Exception; 
	
	
	/**
	 * 查看供应商信息
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Provider getProById(@Param("id")String id)throws Exception; 
	
	/**
	 * 修改供应商信息
	 * @param connection
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public int modify(Provider provider)throws Exception;
	/**
	 * 通过供应商编码查找
	 * @param connection
	 * @param proCode
	 * @return
	 * @throws Exception
	 */
	public Provider getpro(@Param("proCode")String proCode)throws Exception;
	/**
	 * 查询所有信息
	 * 
	 */
	public List<Provider> getproList()throws Exception;
}
