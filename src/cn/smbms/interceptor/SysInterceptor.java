package cn.smbms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.smbms.pojo.User;
import cn.smbms.tools.Constants;



public class SysInterceptor extends HandlerInterceptorAdapter{
      public boolean preHandle(HttpServletRequest request,
    		            HttpServletResponse response,Object handler) throws Exception{
    	  
    	   HttpSession session =request.getSession();
    	   if (request.getRequestURI().indexOf("login") > -1) {
   			return true;
   		 }
    	   User user=(User)session.getAttribute(Constants.USER_SESSION);
    	   if(user==null){
    		   response.sendRedirect(request.getContextPath()+"/error.jsp");
    		   return false;
    	   }
    	  return true;
      }
}
