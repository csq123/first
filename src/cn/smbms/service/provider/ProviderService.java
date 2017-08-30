package cn.smbms.service.provider;

import java.util.List;

import cn.smbms.pojo.Provider;




public interface ProviderService {
	
	/**
	 * 增加用户信息
	 * @param provider
	 * @return
	 */
	public boolean add(Provider provider);
	/**
	 * 根据条件查询供应商列表
	 * @param queryProName
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public List<Provider> getProList(String queryProcode,String queryProname,int currentPageNo, int pageSize);
	/**
	 * 根据条件查询供应商表记录数
	 * @param queryProName
	 * @return
	 */
	public int getProviderCount(String queryProcode,String queryProname);
	
	/**
	 * 根据proCode查询出Provider
	 * @param userCode
	 * @return
	 */
	public Provider selectProCodeExist(String proCode);
	
	/**
	 * 根据ID删除Provider
	 * @param delId
	 * @return
	 */
	public int deleteProviderById(String delId);
	
	/**
	 * 根据ID查找Provider
	 * @param id
	 * @return
	 */
	public Provider getProviderById(String id);
	
	/**
	 * 修改用户信息
	 * @param Provider
	 * @return
	 */
	public boolean modify(Provider provider);
	
	/**
	 * 查询所有信息
	 */
	public List<Provider> getProList();
}
