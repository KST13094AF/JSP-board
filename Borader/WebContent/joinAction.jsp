<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPW" />
<jsp:setProperty name="user" property="userNum"/>
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" />
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>JSP 게시판</title>
</head>
<body>
	<%
	String userID = null;
if(session.getAttribute("userID") != null){
	userID = (String) session.getAttribute("userID");
}
if(userID != null){
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("alert('이미 로그인 되어있습니다.')");
	script.println("location.href = 'main.jsp'");
	script.println("</script>");
}
		if(user.getUserID() == null || user.getUserPW() == null || user.getUserName() ==null || user.getUserGender() == null || user.getUserEmail() ==null || user.getUserNum() == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('모든 사항에 입력해주십시오.')");
			script.println("history.back();");
			script.println("</script>");
		} else{
			UserDAO userDAO = new UserDAO();
			int result = userDAO.join(user);
			if(result == -1){
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디입니다.')");
				script.println("history.back();");
				script.println("</script>");
			}
			else{
				session.setAttribute("userID", user.getUserID()); 
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'main.jsp' ");
				script.println("</script>");
			}
		}
	%>
</body>
</html>