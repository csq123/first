<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!--视图界面  -->
  </head>
  
  <body>
    Hello Spring MVC <br>
 <h1>username----------> ${username} </h1>
 <h1>username----------> ${string} </h1>
 <h1>username1----------> ${currentUser.userName} </h1>
 <h1>username2----------> ${user.userName} </h1>
 <form action="${pageContext.request.contextPath }/index4" method="get">
    <table>
    	<tr>
    	   <td>请输入userCode：</td>
    	   <td><input type="text" name="usercode"></td>
    	</tr>
        <tr>
           <td colspan="2"><input type="submit" value="提交"></td>
        </tr>
    </table>
    </form>
  </body>
</html>
