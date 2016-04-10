<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@page import="com.soundcloud.model.User"%>
<%@page import="java.util.List"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	if(request.getSession().getAttribute("currentUser") == null)
		response.sendRedirect("./login.jsp");
%>
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/customHeader.css" />
</head>
<div class="container">
  <ul class="nav nav-pills">
    <li class="<c:if test="${sessionScope.activeTab == 'alltracks'}">active</c:if>">
    <a data-toggle="pill" href="#home" >My tracks</a></li>
    <li class="<c:if test="${sessionScope.activeTab == 'allplaylists'}">active</c:if>">
    <a data-toggle="pill" href="#menu1">My playlists</a></li>
    <li class="<c:if test="${sessionScope.activeTab == 'alllikes'}">active</c:if>">
    <a data-toggle="pill" href="#menu2">My likes</a></li>
  </ul>
  
  <br>
  <div class="tab-content">
    
    <div id="home" class='tab-pane fade <c:if test="${sessionScope.activeTab == 'alltracks'}">in active</c:if>'>
      <h3>All Tracks</h3><br>
		<c:forEach var="track" items="${sessionScope.tracksToDisplay}">
			<c:set var="track" value="${track}" scope="request"></c:set>
			<jsp:include page="song.jsp"></jsp:include>
		</c:forEach>
		<c:if test="${empty sessionScope.tracksToDisplay}">
			<p> You have no tracks </p>
		</c:if>
		<form action="./NextPreviousTrackPage" method="get">
			<button type="submit" name="nextTracks">NEXT</button>
			<button type="submit" name="previousTracks">PREVIOUS</button>
		</form>
    </div>
    
    <div id="menu1" class='tab-pane fade <c:if test="${sessionScope.activeTab == 'allplaylists'}">in active</c:if>'>
      <h3>All Playlists</h3><br>
        <c:forEach var="playlist" items="${sessionScope.playlistsToDisplay}">
			<c:set var="playlist" value="${playlist}" scope="request"></c:set>
			<jsp:include page="playlist.jsp"></jsp:include>
		</c:forEach>
		<c:if test="${empty sessionScope.playlistsToDisplay}">
			<p> You have no playlists </p>
		</c:if>
		
			<button type="submit" name="nextPlaylists">NEXT</button>
			<button type="submit" name="previousPlaylists">PREVIOUS</button>

    </div>
    
    <div id="menu2" class='tab-pane fade <c:if test="${sessionScope.activeTab == 'alllikes'}">in active</c:if>'>
      <h3>All Likes</h3><br>
     	<c:forEach var="track" items="${sessionScope.likedTracksToDisplay}">
			<c:set var="track" value="${track}" scope="request"></c:set>
			<jsp:include page="song.jsp"></jsp:include>
		</c:forEach>
		<c:if test="${empty sessionScope.likedTracksToDisplay}">
			<p> You haven't liked any tracks </p>
		</c:if>
		<form action="./NextPreviousTrackPage" method="get">
			<button type="submit" name="nextLikedTracks">NEXT</button>
			<button type="submit" name="previousLikedTracks">PREVIOUS</button>
		</form>
    </div>
  </div>
</div>