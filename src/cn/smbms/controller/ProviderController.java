package cn.smbms.controller;
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


import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;

import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
/**
 * 供应商控制页面
 * @author Administrator
 
 */
@Controller

public class ProviderController {
	    @Resource
       private ProviderService providerservice;
	    @RequestMapping(value="/sys/provider/providerlist.html")
	    public String providerList(Model model,
	    		                   @RequestParam(value="queryProName" ,required=false) String queryProName,
	    		                   @RequestParam(value="queryProCode" ,required=false) String queryProCode,
	    		                   @RequestParam(value="pageIndex",required=false) String pageIndex
	    		                ){
	    	
	    	if(queryProName==null){
				queryProName = "";
			}
			if(queryProCode==null){
				queryProCode = "";
			}
			//设置页面容量
			List<Provider> providerList =null;
	     	int pageSize = Constants.pageSize;
	     	//当前页码
	     	int currentPageNo = 1;
	     	if(pageIndex != null){
	     		try{
	     			currentPageNo = Integer.valueOf(pageIndex);
	     		}catch(NumberFormatException e){
	     			return "redirect:/sys/user/syserror.html";
	     		}
	     	}
	     	//总数量（表）	
	     	int totalCount=providerservice.getProviderCount(queryProCode, queryProName);
	     	
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
			providerList = providerservice.getProList(queryProCode,queryProName,currentPageNo,pageSize);
			model.addAttribute("providerList", providerList);
			model.addAttribute("queryProName", queryProName);
			model.addAttribute("queryProCode", queryProCode);
			model.addAttribute("totalPageCount", totalPageCount);
	 		model.addAttribute("totalCount", totalCount);
	 		model.addAttribute("currentPageNo", currentPageNo);
	 	
			return "providerlist";
	    }
	    //查看信息
	    @RequestMapping(value="/sys/provider/providerview/{id}",method=RequestMethod.GET)
	    public String getProviderId(Model model,
		        @PathVariable String id ){
	  
	     Provider provider=providerservice.getProviderById(id);
	     model.addAttribute("provider", provider);
	     return "providerview";
	    }
	    //删除供应商信息
	    @RequestMapping(value="/provider/deleteProvider")
	    @ResponseBody
	    public String deleteProvider(@RequestParam String proid){
	    	HashMap<String, String> resultMap = new HashMap<String, String>();
			if(!StringUtils.isNullOrEmpty(proid)){
				int flag = providerservice.deleteProviderById(proid);
				if(flag == 0){//删除成功
					resultMap.put("delResult", "true");
				}else if(flag == -1){//删除失败
					resultMap.put("delResult", "false");
				}else if(flag > 0){//该供应商下有订单，不能删除，返回订单数
					resultMap.put("delResult", String.valueOf(flag));
				}
			}else{
				resultMap.put("delResult", "notexit");
			}
			return JSONArray.toJSONString(resultMap);
	    }
	    //添加跳转页面
	    @RequestMapping(value="/sys/provider/provideradd.html",method=RequestMethod.GET)
	    public String addUser(@ModelAttribute("provider") Provider provider){
	 	  
	 	   return "provideradd";
	    }
	    //保存新增用户信息
	    @RequestMapping(value="/provider/addsave.html",method=RequestMethod.POST)
	    public String add(Provider provider,HttpSession session){
	    	provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
	    	provider.setCreationDate(new Date());
	   	   if(providerservice.add(provider)){
	   		   return "redirect:/sys/provider/providerlist.html";
	   	   }
	   	   
	   	   return "provideradd";
	    }
	  //根据用户ID获取供应商信息
	    @RequestMapping(value="/sys/provider/providermodify.html")
	    public String getUserById(@RequestParam String proid,Model model){
	 	  
	 	  Provider provider= providerservice.getProviderById(proid);
	 	   model.addAttribute(provider);
	 	  return "providermodify";
	    }
	    //保存修改的供应商信息
	    @RequestMapping(value="/provider/providermodifysave.html",method=RequestMethod.POST)
	    public String modifyProviderSave(Provider provider ,HttpSession session){
	    	provider.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
	    	provider.setModifyDate(new Date());
	 	   if(providerservice.modify(provider)){
	 		  return "redirect:/sys/provider/providerlist.html";
	 	   }
	 	  return "providermodify";
	    }
}
