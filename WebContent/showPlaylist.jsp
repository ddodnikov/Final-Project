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
<title>Home :: SoundCloud</title>
</head>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<c:if test="${not empty sessionScope.showUser}">
				<jsp:include page="./showUserProfile.jsp"></jsp:include>
			</c:if>
			<br>
			<h1> Playlist : ${sessionScope.showPlaylist.title} </h1>
			<br>
			<c:forEach var="track" items="${sessionScope.playlistTracks}">
				<c:set var="track" value="${track}" scope="request"></c:set>
				<jsp:include page="song.jsp"></jsp:include>
			</c:forEach>
			<c:if test="${empty sessionScope.playlistTracks}">
				<p> You have no tracks in the playlist</p>
			</c:if>
			<form action="./NextPreviousTrackPage" method="get" class="pages">
				<button type="submit" name="previousTracks" class="previousButton"></button>
				<button type="submit" name="nextTracks" class="nextButton"></button>
			</form>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>