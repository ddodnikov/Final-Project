<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/home.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>Home :: SoundCloud</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<h1>Upload track</h1>
			<c:if test="${not empty sessionScope.userId}">
				<p>${sessionScope.userId}</p>
			</c:if>
			<form action="./UploadSong" method="post"
				enctype="multipart/form-data">
				<label for="fileUpload">Select file to upload:</label> <input
					type="file" name="fileUpload" size="50" /> <br /> <input
					type="submit" value="Upload Track" />
				<c:if test="${not empty message}">
					<p style="color: red">${message}</p>
				</c:if>
			</form>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>