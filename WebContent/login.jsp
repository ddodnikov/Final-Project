<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/login.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>Log in :: SoundCloud</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<form action="./Login" method="post">
				<table cellpadding="10">
					<tr>
						<td colspan=2><label for="email">Email:</label></br>
						<input
							type="email" name="email"/></td>
					</tr>
					<tr>
						<td><label for="password1">Password:</label></br> <input
							type="password" name="password" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="Log in" /></td>
					</tr>
				</table>

				
				<c:if test="${not empty wrongUser}">
					<p style="color: red">${wrongUser}</p>
				</c:if>

			</form>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>