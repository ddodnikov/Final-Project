<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	if(request.getSession().getAttribute("currentUser") == null)
		response.sendRedirect("./login.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/home.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>SoundCloud :: IT Talents</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			
			<jsp:include page="./showUserProfile.jsp"></jsp:include>
			<jsp:include page="./showCustomHeader.jsp"></jsp:include>
			
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>