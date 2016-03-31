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
    <li class="active"><a data-toggle="pill" href="#home">My tracks</a></li>
    <li><a data-toggle="pill" href="#menu1">My playlists</a></li>
    <li><a data-toggle="pill" href="#menu2">My likes</a></li>
    <li><a data-toggle="pill" href="#menu3">My follows</a></li>
  </ul>
  
  <br>
  <div class="tab-content">
    
    <div id="home" class="tab-pane fade in active">
      <h3>All Tracks</h3><br>
      	<% 
			List<Track> tracks = new TrackDAO().getUserTracks((int)session.getAttribute("userId"));
			request.setAttribute("tracks", tracks);
		%>
		<c:forEach var="track" items="${tracks}">
			<c:set var="track" value="${track}" scope="request"></c:set>
			<jsp:include page="song.jsp"></jsp:include>
		</c:forEach>
		<c:if test="${empty tracks}">
			<p> You have no tracks </p>
		</c:if>
    </div>
    
    <div id="menu1" class="tab-pane fade">
      <h3>All Playlists</h3><br>
    </div>
    
    <div id="menu2" class="tab-pane fade">
      <h3>All Likes</h3><br>
     
    </div>
    
    <div id="menu3" class="tab-pane fade">
      <h3>All Follows</h3><br>
      
    </div>
  </div>
</div>