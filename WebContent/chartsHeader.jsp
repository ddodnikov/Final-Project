<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@page import="java.util.List"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/customHeader.css" />
</head>
<div class="container">
  <ul class="nav nav-pills">
    <li class="active"><a data-toggle="pill" href="#home">Top 50</a></li>
    <li><a data-toggle="pill" href="#menu1">Latest Uploads</a></li>
    <li><a data-toggle="pill" href="#menu2">Most Liked</a></li>
  </ul>
  
  <br>
  <div class="tab-content">
    
    <div id="home" class="tab-pane fade in active">
      <h3>Top 50 most listened tracks</h3><br>
		  <% 
			List<Track> playedTracks = new TrackDAO().getMostPlayedTracks();
			request.setAttribute("playedTracks", playedTracks);
		%>
      	<c:forEach var="track" items="${playedTracks}">
			<c:set var="track" value="${track}" scope="request"></c:set>
			<jsp:include page="song.jsp"></jsp:include>		
		</c:forEach>
    </div>
    
    <div id="menu1" class="tab-pane fade">
      <h3>Last uploaded tracks</h3><br>
     	 <% 
			List<Track> latestTracks = new TrackDAO().getLatestTracks();
			request.setAttribute("latestTracks", latestTracks);
		%>
      	<c:forEach var="track" items="${latestTracks}">
			<c:set var="track" value="${track}" scope="request"></c:set>
			<jsp:include page="song.jsp"></jsp:include>		
		</c:forEach>
    </div>
    
    <div id="menu2" class="tab-pane fade">
      <h3>Most likes tracks</h3><br>
     	<% 
			List<Track> likedTracks = new TrackDAO().getMostLikedTracks();
			request.setAttribute("likedTracks", likedTracks);
		%>
      	<c:forEach var="track" items="${likedTracks}">
			<c:set var="track" value="${track}" scope="request"></c:set>
			<jsp:include page="song.jsp"></jsp:include>		
		</c:forEach>
    </div>
 
  </div>
</div>