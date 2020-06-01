<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO"%>
<%@ page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page" />
<jsp:setProperty name="bbs" property="bbsTitle" />
<jsp:setProperty name="bbs" property="bbsContent" />
<jsp:setProperty name="bbs" property="userEmail"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>JSP 게시판</title>
</head>
<body>
	<%
	String userID = null;
	String userEmail = null;
if(session.getAttribute("userID") != null){
	userID = (String) session.getAttribute("userID");
	BbsDAO bbsDAO = new BbsDAO();
	userEmail = bbsDAO.getEamil(userID);
}
if(userID == null){
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("alert('로그인을 하세요.')");
	script.println("location.href = 'login.jsp'");
	script.println("</script>");
}else{
	if(bbs.getBbsTitle() == null || bbs.getBbsContent() == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('모든 사항에 입력해주십시오.')");
		script.println("history.back();");
		script.println("</script>");
	} else{
		BbsDAO bbsDAO = new BbsDAO();
		int result = bbsDAO.write(bbs.getBbsTitle(), userID, userEmail, bbs.getBbsContent());
		if(result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('글쓰기에 실패했습니다.')");
			script.println("history.back();");
			script.println("</script>");
		}
		else{
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'bbs.jsp' ");
			script.println("</script>");
		}
	}
}	
	%>
</body>
</html>