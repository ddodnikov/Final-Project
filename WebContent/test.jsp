<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="scripts/jquery.jplayer.min.js"></script>
<link type="text/css" href="skin/pink.flag/css/jplayer.pink.flag.css"
	rel="stylesheet" />
<title>Insert title here</title>
</head>
<body>
<% Track track = new TrackDAO().getTrackById(24);
	request.setAttribute("track1", track); %>
<jsp:include page="jPlayer.jsp"></jsp:include>
<script src="scripts/loadJPlayer.js" data-id="${track.id}"
	data-title="${track.title}" data-url="./FetchTrack?trackId=${track.id}"></script>
</body>
</html>