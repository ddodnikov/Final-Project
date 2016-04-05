<%@page import="com.soundcloud.model.UserDAO"%>
<%@page import="com.soundcloud.model.User"%>
<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@page import="java.util.List"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/customHeader.css" />
<link type="text/css" rel="stylesheet" href="styles/global.css" />
</head>
<div class="container">
  <ul class="nav nav-pills">
    <li class="active"><a data-toggle="pill" href="#home">Tracks</a></li>
    <li><a data-toggle="pill" href="#menu1">Playlists</a></li>
  </ul>
  
  <br>
  <div class="tab-content">
    
    <div id="home" class="tab-pane fade in active">
      <h3>All Tracks</h3><br>
		<c:forEach var="track" items="${sessionScope.showTracks}">
			<c:set var="track" value="${track}" scope="request"></c:set>
			<jsp:include page="song.jsp"></jsp:include>
		</c:forEach>
		<form action="./NextPreviousTrackPageShowUser" method="get">
			<button type="submit" name="next">NEXT</button>
			<button type="submit" name="previous">PREVIOUS</button>
		</form>
		<c:if test="${empty sessionScope.showTracks}">
			<p> No tracks </p>
		</c:if>
    </div>
    
    <div id="menu1" class="tab-pane fade">
      <h3>All Playlists</h3><br>
      
    </div>

  </div>
</div>