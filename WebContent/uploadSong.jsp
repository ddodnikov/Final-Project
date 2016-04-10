<%@page import="java.util.List"%>
<%@page import="com.soundcloud.model.GenreDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	if (request.getSession().getAttribute("currentUser") == null) {
		response.sendRedirect("./login.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="scripts/tagManager.js"></script>
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/uploadSong.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>Home :: SoundCloud</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<h1>Upload track</h1>
			<form method="post" action="./UploadSong" enctype="multipart/form-data">
				<div id="left">
					<input type="file" name="songUpload" accept=".mp3" /> <label for="title">Title (required)</label>
					<input type="text" name="title" placeholder="Name your track" />
					<label for="genre">Genre</label>
					<select name="genre">
						<%
							List<String> allGenres = (GenreDAO.getGenreDAOInstance().getAllGenreNames());
							request.setAttribute("allGenres", allGenres);
						%>
						<c:forEach var="genreName" items="#{allGenres}">
							<option value="${genreName}">${genreName}</option>
						</c:forEach>
					</select>
					<label for="tags">Tags - describe the genre and mood of your track</label>
					<div id="tags">
						<input type="text" value="" placeholder="Add tags" />
					</div>
					
					<!-- <input type="text" name="tags" placeholder="Add tags to describe the genre and mood of your track" /> -->
					<label for="description">Description</label>
					<textarea name="description" rows=5 cols=45 placeholder="Describe your track"></textarea>
				</div>
				<div id="right">
					<div id="trackPic">
						<label for="currentProfile">Track picture:</label>
						<img src="./images/defaultSongImage.jpg" id="outImage" />
						<input name="uploadTrackPic" type="file" id="picField" accept="image/jpeg" />
						<script src="scripts/dynamicTrackPicLoad.js"></script>
					</div>
					<input type="submit" value="Upload">
				</div>
			</form>
			<c:if test="${not empty songErrorMessage}">
				<p id="message">${songErrorMessage}</p>
			</c:if>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>