<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<title>Search results :: SoundCloud</title>
</head>
<body>
<div id="outer">
	<jsp:include page="header.jsp"></jsp:include>
	<div id="wrapper">
	<c:choose>
		<c:when test="${not empty noResultsMessage}">
			<p id="message">${noResultsMessage}</p>
		</c:when>
		<c:otherwise>
			<c:forEach var="tracksList" items="${results}">
				<c:set var="tracksList" value="${tracksList}" scope="request"></c:set>
				<c:forEach var="track" items="${tracksList}">
					<p>${track.title}</p>
				</c:forEach>			
			</c:forEach>
		</c:otherwise>
	</c:choose>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>