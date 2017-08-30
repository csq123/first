package cn.smbms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;


import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
/**
 * 订单管理控制页面
 * @author Administrator
 *
 */
@Controller

public class BillController {
	       @Resource
           private BillService billservice;
	       @Resource
	       private ProviderService providerservice;
	       @RequestMapping(value="/sys/bill/billlist.html")
	       public String getBillList(Model model,
                   @RequestParam(value="queryProductName",required=false) String queryProductName,
                   @RequestParam(value="queryProviderId",required=false) String queryProviderId,
                   @RequestParam(value="queryIsPayment",required=false) String queryIsPayment,
                   @RequestParam(value="pageIndex",required=false) String pageIndex
                  ){
	    	   
	    	   List<Provider> providerList = new ArrayList<Provider>();
	    	   try {
				providerList = providerservice.getProList();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	   	model.addAttribute("providerList", providerList);
	//设置页面容量
	int pageSize = Constants.pageSize;
	//当前页码
	int currentPageNo = 1;
	if(StringUtils.isNullOrEmpty(queryProductName)){
		queryProductName = "";
	}
	List<Bill> billList = new ArrayList<Bill>();
	
	int queryIsPayments=0;
	if(StringUtils.isNullOrEmpty(queryIsPayment)){
		 queryIsPayments=0;
	}else{
		queryIsPayments=(Integer.parseInt(queryIsPayment));
	}
	int queryProviderIds=0;
	if(StringUtils.isNullOrEmpty(queryProviderId)){
		queryProviderIds=0;
	}else{
		queryProviderIds=(Integer.parseInt(queryProviderId));
	}
	
	if(pageIndex != null){
		try{
			currentPageNo = Integer.valueOf(pageIndex);
		}catch(NumberFormatException e){
			return "redirect:/sys/user/syserror.html";
		}
	}	
	//总数量（表）	
	int totalCount	= billservice.getBillCount(queryProductName, queryProviderIds, queryIsPayments);
	
	//总页数
	PageSupport pages=new PageSupport();
	pages.setCurrentPageNo(currentPageNo);
	pages.setPageSize(pageSize);
	pages.setTotalCount(totalCount);
	int totalPageCount = pages.getTotalPageCount();
	//控制首页和尾页
	if(currentPageNo < 1){
		currentPageNo = 1;
	}else if(currentPageNo > totalPageCount){
		currentPageNo = totalPageCount;
	}
	billList = billservice.getBillList(queryProductName, queryProviderIds, queryIsPayments, currentPageNo, pageSize);
	model.addAttribute("billList", billList);
	model.addAttribute("queryProductName", queryProductName);
	model.addAttribute("queryProviderId", queryProviderIds);
	model.addAttribute("queryIsPayment", queryIsPayments);
	model.addAttribute("totalPageCount", totalPageCount);
	model.addAttribute("totalCount", totalCount);
	model.addAttribute("currentPageNo", currentPageNo);
	return "billlist";
}
	//查看个人信息
	@RequestMapping(value="/sys/bill/billview/{id}",method=RequestMethod.GET)       
	public String getBillById( Model model,@PathVariable String id){
		   
		   Bill bill= billservice.getBillById(id);
		   model.addAttribute("bill", bill);
		   return "billview";
	}
	//删除订单信息
	 @RequestMapping(value="/bill/deleteBill")
	    @ResponseBody
	    public String deleteProvider(@RequestParam String billid){
	    	HashMap<String, String> resultMap = new HashMap<String, String>();
			if(!StringUtils.isNullOrEmpty(billid)){
				boolean  flag = billservice.deleteBillById(billid);
				 if(flag){//删除成功
						resultMap.put("delResult", "true");
					}else{//删除失败
						resultMap.put("delResult", "false");
					}
				}else{
					resultMap.put("delResult", "notexit");
	    }
			return JSONArray.toJSONString(resultMap);
	 }
	 //
	 @RequestMapping(value="bill/proList")
	   @ResponseBody
	   //获取供应商列表
	   public String getProviderList(){
		     List<Provider> ProviderList = null;
		     try {
				ProviderList = providerservice.getProList();
			} catch (Exception e) {
			   	
				e.printStackTrace();
			}
			return JSONArray.toJSONString(ProviderList);
	   }
	 //添加跳转页面
	   @RequestMapping(value="/sys/bill/billadd.html",method=RequestMethod.GET)
	   public String addUser(@ModelAttribute("bill") Bill bill){
		  
		   return "billadd";
	   }
	 //保存新增订单信息
	    @RequestMapping(value="/bill/addsave.html",method=RequestMethod.POST)
	    public String add(Bill bill,HttpSession session){
	    	bill.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
	    	bill.setCreationDate(new Date());
	   	   if(billservice.add(bill)){
	   		   return "redirect:/sys/bill/billlist.html";
	   	   }
	   	   return "billadd";
	    }
	    //根据用户ID获取订单信息
	    @RequestMapping(value="/sys/bill/billmodify.html")
	    public String getUserById(@RequestParam String billid,Model model){
	 	  
	 	  Bill bill= billservice.getBillById(billid);
	 	   model.addAttribute(bill);
	 	  return "billmodify";
	    }
	    //保存修改的供应商信息
	    @RequestMapping(value="/bill/billmodifysave.html",method=RequestMethod.POST)
	    public String modifyProviderSave(Bill bill ,HttpSession session){
	    	bill.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
	    	bill.setModifyDate(new Date());
	 	   if(billservice.modify(bill)){
	 		  return "redirect:/sys/bill/billlist.html";
	 	   }
	 	  return "billmodify";
	    }
}

