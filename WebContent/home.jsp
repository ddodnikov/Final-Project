<%@page import="com.soundcloud.model.TrackDAO"%>
<<<<<<< HEAD
<%@page import="com.soundcloud.model.Track"%>
=======
<%@page import="com.soundcloud.model.Track" %>
>>>>>>> 06a9d37bd715223ce15964e8d73b47fcfefb6dde
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<<<<<<< HEAD
<link type="text/css" rel="stylesheet" href="styles/home.css" />
=======
<link type="text/css" rel="stylesheet" href="styles/register.css" />
>>>>>>> 06a9d37bd715223ce15964e8d73b47fcfefb6dde
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>Home :: SoundCloud</title>
</head>
<body>
<<<<<<< HEAD
	<%
		List<Track> tracks = new TrackDAO().getAllTracks();
	%>
=======

	<% List<Track> tracks = new TrackDAO().getAllTracks(); %>

>>>>>>> 06a9d37bd715223ce15964e8d73b47fcfefb6dde
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
		<h1>Upload your tracks</h1>
			<form action="./UploadSong" method="post"
				enctype="multipart/form-data">
				<label for="fileUpload">Select file to upload:</label>
				<input type="file" name="fileUpload"
					size="50" /> <br /> <input type="submit" value="Upload File" />
				<c:if test="${not empty message}">
					<p style="color: red">${message}</p>
				</c:if>

			</form>
<<<<<<< HEAD
			<c:forEach var="track" items="${tracks}">
				<c:set var="track" value="${track}" scope="request"></c:set>
				<jsp:include page="song.jsp"></jsp:include>
			</c:forEach>
		</div>

=======
			
			<% for(Track track : tracks)  { 
				request.setAttribute("track", track); %>
			<jsp:include page="song.jsp"></jsp:include>
			<% } %>
		
		</div>
		
>>>>>>> 06a9d37bd715223ce15964e8d73b47fcfefb6dde
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>