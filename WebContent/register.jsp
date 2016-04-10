<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/register.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>Register :: SoundCloud</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<h1>Join us today!</h1>
			<c:if test="${not empty sessionScope.userId}">
				<p>${sessionScope.userId}</p>
			</c:if>
			<form action="./Register" method="post">
				<table cellpadding="10">
					<tr>
						<td colspan=2><label for="email">Email:</label></br> <input
							type="email" name="email" required/></td>
					</tr>
					<tr>
						<td><label for="password1">Choose password:</label></br> <input
							type="password" name="password1" required/></td>
						<td><label for="password2">Repeat password:</label></br> <input
							type="password" name="password2" required/></td>
					</tr>
					<tr>
						<td colspan=2><input type="submit" value="Register!" /></td>
					</tr>
				</table>
				<p class="message error">
					<c:if test="${not empty usedEmailError}">
						${usedEmailError}
					</c:if>
					<c:if test="${not empty shortPassword}">
						${shortPassword}
					</c:if>
					<c:if test="${not empty differentPasswords}">
						${differentPasswords}
					</c:if>
				</p>		
			</form>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>