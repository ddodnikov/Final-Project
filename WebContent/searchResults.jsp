<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
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
			<c:forEach var="track" items="${sessionScope.results}">
				<c:set var="track" value="${track}" scope="request"></c:set>
				<jsp:include page="song.jsp"></jsp:include>		
			</c:forEach>
		<form action="./NextPreviousSearchPage" method="get">
			<button type="submit" name="nextSearchTracks">NEXT</button>
			<button type="submit" name="previousSearchTracks">PREVIOUS</button>
		</form>
		</c:otherwise>
	</c:choose>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>