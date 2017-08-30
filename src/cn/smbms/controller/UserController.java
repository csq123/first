package cn.smbms.controller;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;


/**
 * 用户管理页面控制器
 * @author Administrator
 *
 */
@Controller

public class UserController  {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleservice;
 
   //实现跳转到登录页
   @RequestMapping(value="/user/login")
   public String login(){
	  
	   return "login";		   
   }
   //实现登录
   @RequestMapping(value="/user/dologin.html",method=RequestMethod.POST)
   public String doLogin(@RequestParam String userCode,@RequestParam String userPassword
		                  ,HttpSession session,HttpServletRequest request){
	  

	   User user=userService.login(userCode,userPassword);
	   if(user!=null){
		   //放入session
		   session.setAttribute(Constants.USER_SESSION, user);
		   session.removeAttribute(Constants.SYS_MESSAGE);
		   //页面跳转（frame.jap）
		   return "redirect:/sys/user/main.html";//页面重定向操作
	   }else{
		   request.setAttribute("error", "用户名或密码不正确！！！");
		   return "login";
	   }
   }
   @RequestMapping(value="/sys/user/main.html")
   public String main(){
	 
	   return "frame";
   }
   //实现退出
   
   @RequestMapping(value="/user/out.html")
   public String out(HttpSession session){
	 //清除session
	   session.removeAttribute(Constants.USER_SESSION);
	   return "login";
   }
   //异常
   @RequestMapping(value="exlogin.html",method=RequestMethod.GET)
   public String exLogin(@RequestParam String userCode,@RequestParam String userPassword){
	 
	   User user=userService.login(userCode, userPassword);
	   if(user==null){
		   throw new RuntimeException("用户名或者密码不正确！！！");
	   }
	   return "redirect:/sys/user/main.html";
   }
   //局部异常
  /* @ExceptionHandler(value={RuntimeException.class})
   public String handelException(RuntimeException e,HttpServletRequest request){
	   request.setAttribute("e", e);
	   return "error";
   }*/
   
   /**
    * 查询
    * @return
    */
   @RequestMapping (value="/sys/user/userlist.html")
   public String getUserList(Model model,
		                     @RequestParam(value="queryname",required=false) String queryUserName,
		                     @RequestParam(value="queryUserRole",required=false) String queryUserRole,
		                     @RequestParam(value="pageIndex",required=false) String pageIndex
		                    ){
	  
	 		int _queryUserRole = 0;
	 		List<User> userList = null;
	 		//设置页面容量
	     	int pageSize = Constants.pageSize;
	     	//当前页码
	     	int currentPageNo = 1;
	 		if(queryUserName == null){
	 			queryUserName = "";
	 		}
	 		if(queryUserRole != null && !queryUserRole.equals("")){
	 			_queryUserRole = Integer.parseInt(queryUserRole);
	 		}
	 		
	     	if(pageIndex != null){
	     		try{
	     			currentPageNo = Integer.valueOf(pageIndex);
	     		}catch(NumberFormatException e){
	     			return "redirect:/sys/user/syserror.html";
	     		}
	     	}	
	     	//总数量（表）	
	     	int totalCount	= userService.getUserCount(queryUserName,_queryUserRole);
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
	 		userList = userService.getUserList(queryUserName,_queryUserRole,currentPageNo, pageSize);
	 		model.addAttribute("userList", userList);
	 		List<Role> roleList = null;
	 		roleList = roleservice.getRoleList();
	 		model.addAttribute("roleList", roleList);
	 		model.addAttribute("queryUserName", queryUserName);
	 		model.addAttribute("queryUserRole", queryUserRole);
	 		model.addAttribute("totalPageCount", totalPageCount);
	 		model.addAttribute("totalCount", totalCount);
	 		model.addAttribute("currentPageNo", currentPageNo);
	 		return "userlist";
   }
   //查看个人信息
   @RequestMapping(value="/sys/user/userview/{id}",method=RequestMethod.GET)
   public String getUserById(Model model,
		        @PathVariable String id ){
	   
			User user = userService.getUserById(id);
			model.addAttribute("user", user);
			return "userview"; 
   }
   //验证用户名是否重复
   @RequestMapping(value="/user/ucexist")
   @ResponseBody
   public Object userCodeIsExit(@RequestParam String userCode){
	   HashMap<String, String> resultMap=new HashMap<String, String>();
	   if(StringUtils.isNullOrEmpty(userCode)){
		   resultMap.put("userCode", "exist");
	   }else{
		   User user=userService.selectUserCodeExist(userCode);
		   if(user!=null){
			   resultMap.put("userCode", "exist");
		   }else{
			   resultMap.put("userCode", "noexist");
		   }
	   } 
	   return JSONArray.toJSONString(resultMap);
   }
   //注解中@RequestMapping加/*,produces={"application/json;charset=UTF-8"}*/
   @RequestMapping(value="/user/roleList")
   @ResponseBody
   //获取用户角色列表
   public String getRoleList(){
	     List<Role> roleList = null;
		roleList = roleservice.getRoleList();
		return JSONArray.toJSONString(roleList);
   }
   //添加跳转页面
   @RequestMapping(value="/sys/user/useradd.html",method=RequestMethod.GET)
   public String addUser(@ModelAttribute("user") User user){
	  
	   return "useradd";
   }
   //保存新增用户信息
   @RequestMapping(value="/user/addsave.html",method=RequestMethod.POST)
   public String addUserSave(User user,HttpSession session,
		                     HttpServletRequest request,
		                      @RequestParam(value="attachs",required=false) MultipartFile[] attachs){
	   String idPicPath=null;
	   String workPicPath=null;
	   String totalPath=null;
	   String totalPaths=null;
	   String errorInfo=null;
	   boolean flag=true;
	   String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
	   for(int i=0;i<attachs.length;i++){
		   MultipartFile attach=attachs[i];
		   
		   //判断文件是否为空
		   if(!attach.isEmpty()){
			   if(i==0){
				   errorInfo="uploadFileError";
			   }else if(i==1){
				   errorInfo="uploadWpError";
			   }
			   String oldFileName=attach.getOriginalFilename();//原文件名
			  
			   String prefix=FilenameUtils.getExtension(oldFileName);//原文件名后缀
			   int filesize=5000000;
			   if(attach.getSize()>filesize){//上传文件大小不得超过500kb
				   request.setAttribute(errorInfo, "上传文件大小不得超过500kb");
				   flag=false;
			   }else if(prefix.equalsIgnoreCase("jpg")||
					     prefix.equalsIgnoreCase("png")||
					     prefix.equalsIgnoreCase("jpeg")||
					     prefix.equalsIgnoreCase("peng")){//上传图片格式不正确
				 //当前系统时间+随机数+"_Personal.jpg"
				    String fileName=System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";//保证文件名不重复
				   
				    File targetFile=new File(path,fileName);
				    if(!targetFile.exists()){
				    	targetFile.mkdirs();
				    }
				    //保存文件到服务器
				    try {
						attach.transferTo(targetFile);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute(errorInfo, "* 上传失败！！！");
						flag=false;
					}
				    if(i==0){
				    	 idPicPath=path+File.separator+fileName;
				    	 totalPath=File.separator+"statics"+File.separator+"uploadfiles"+File.separator+fileName;
				    }else if(i==1){
				    	workPicPath=path+File.separator+fileName;
				    	totalPaths=File.separator+"statics"+File.separator+"uploadfiles"+File.separator+fileName;
				    }
			   }else{
				   request.setAttribute(errorInfo, "上传图片格式不正确");
				   flag=false;
			   }
		   }
	   }
	   if(flag){
	   user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
	   user.setCreationDate(new Date());
	   user.setIdPicPath(idPicPath);
	   user.setWorkPicPath(workPicPath);
	   user.setTotalpath(totalPath);
	   user.setTotalpaths(totalPaths);
	   if(userService.add(user)){
		   return "redirect:/sys/user/userlist.html";
	   }
	   }
	   return "useradd";
   }
   //根据用户ID获取用户信息
   @RequestMapping(value="/sys/user/usermodify.html")
   public String getUserById(@RequestParam String uid,Model model){
	   
	  User user= userService.getUserById(uid);
	   model.addAttribute(user);
	   return "usermodify";
   }
   //保存修改的用户信息
   @RequestMapping(value="/user/usermodifysave.html",method=RequestMethod.POST)
   public String modifyUserSave(User user ,HttpSession session){
	   user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
	   user.setModifyDate(new Date());
	   if(userService.modify(user)){
		   return "redirect:/sys/user/userlist.html";
	   }
	   return "usermodify";
   }
   //删除个人信息
   @RequestMapping(value="/sys/user/delUser")
   @ResponseBody
   public String deleteUser(@RequestParam String uid){
	   Integer delId = 0;
			delId = Integer.parseInt(uid);
			
			HashMap<String, String> resultMap = new HashMap<String, String>();
			if(delId <= 0){
				resultMap.put("delResult", "notexist");
			}else{
				if(userService.deleteUserById(delId)){
					resultMap.put("delResult", "true");
				}else{
					resultMap.put("delResult", "false");
				}
			}
			return JSONArray.toJSONString(resultMap);
   }
   //进入密码修改页面
   @RequestMapping(value="/sys/user/pwdModify.html",method=RequestMethod.GET)
   public String pwdModify(@ModelAttribute("user") User user){
	  
	   return "pwdmodify";
	}
	/**
	 * 判断旧密码是否输入正确
	 * @param oldpassword
	 * @param user
	 * @param session
	 * @return
	 */
   @RequestMapping(value="/user/PwdByUserId")
   @ResponseBody
	public String getPwdByUserId(@RequestParam String oldpassword,HttpSession session) {
	   String pasw= ((User)session.getAttribute(Constants.USER_SESSION)).getUserPassword();
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null == pasw ){//session过期
			resultMap.put("result", "sessionerror");
		}else if(StringUtils.isNullOrEmpty(oldpassword)){//旧密码输入为空
			resultMap.put("result", "error");
		}else{
			if(oldpassword.equals(pasw)){
				resultMap.put("result", "true");
			}else{//旧密码输入不正确
				resultMap.put("result", "false");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
   /**
    * 保存修改密码信息
    */
    @RequestMapping(value="/sys/user/pwdSave.html",method=RequestMethod.POST)
	public String pwdSave(@RequestParam String newpassword,HttpSession session){
    	Integer o= ((User)session.getAttribute(Constants.USER_SESSION)).getId();
    	boolean flag = false;
		if(o != null && !StringUtils.isNullOrEmpty(newpassword)){
			flag = userService.updatePwd(o,newpassword);
			if(flag){
				session.setAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
				session.removeAttribute(Constants.USER_SESSION);//session注销
				
			}else{
				session.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
			}
		}else{
			session.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
		    
		}
		return "pwdmodify";
	}
}
