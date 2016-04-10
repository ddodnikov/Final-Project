<% 
	if(request.getSession().getAttribute("currentUser") == null)
		response.sendRedirect("./login.jsp");
%>
<img src="${pageContext.request.contextPath}/userProfilePics/${sessionScope.currentProfilePic}" alt="currentProfilePic" />