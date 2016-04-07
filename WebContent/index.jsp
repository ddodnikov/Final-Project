<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/welcome.css" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<title>SoundCloud :: IT Talents</title>
</head>
<body>
	<script>
		function runServletPreLoad() {
			$.get("Charts");
		}
		window.onpaint = runServletPreLoad();
	</script>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<div id="headerImage">
				<img src="images/headerImage.jpg" alt="Header image" />
				<div id="titles">
					<p id="bigTitle">Welcome to SoundCloud</p>
					<p>Hear the world's sounds.</p>
					<p>Find the music you love.</p>
					<p>Discover new tracks.</p>
				</div>
			</div>
			<div id="charts" class="container">
				<ul class="nav nav-pills">
					<li class="active">
						<a data-toggle="pill" href="#mostPlayedBlock" id="mostPlayed" class="chartLink">
							<section>
								<img src="images/mostPlayed.jpg" alt="Most played" />
								<div class="chartType">
									<p>Most played tracks</p>
								</div>
							</section>
						</a>
					</li>
					<li>
						<a data-toggle="pill" href="#mostLikedBlock" id="mostLiked" class="chartLink">
							<section>
								<img src="images/mostLiked.jpg" alt="Most liked" />
								<div class="chartType">
									<p>Most liked tracks</p>
								</div>
							</section>
						</a>
					</li>
					<li>
						<a data-toggle="pill" href="#lastAddedBlock" id="lastAdded" class="chartLink">
							<section>
								<img src="images/lastAdded.jpg" alt="Last added" />
								<div class="chartType">
									<p>Last added tracks</p>
								</div>
							</section>
						</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="mostPlayedBlock" class="tab-pane fade in active">
						<h3>Top 5 most played tracks</h3>
						<% List<Track> mostPlayed = new TrackDAO().getMostPlayedTracks();
							request.setAttribute("mostPlayedTracks", mostPlayed); %>
						<c:forEach var="track" items="${mostPlayedTracks}">
							<c:set var="track" value="${track}" scope="request"></c:set>
							<jsp:include page="song.jsp"></jsp:include>
						</c:forEach>
					</div>
					<div id="mostLikedBlock" class="tab-pane fade">
						<h3>Top 5 most liked tracks</h3>
						<% List<Track> mostLiked = new TrackDAO().getMostLikedTracks();
							request.setAttribute("mostLikedTracks", mostLiked);%>
						<c:forEach var="track" items="${mostLikedTracks}">
							<c:set var="track" value="${track}" scope="request"></c:set>
							<jsp:include page="song.jsp"></jsp:include>
						</c:forEach>
					</div>
					<div id="lastAddedBlock" class="tab-pane face">
						<h3>Last 5 uploaded tracks</h3>
						<% List<Track> lastAdded = new TrackDAO().getLatestTracks();
							request.setAttribute("lastAddedTracks", lastAdded);%>
						<c:forEach var="track" items="${lastAddedTracks}">
							<c:set var="track" value="${track}" scope="request"></c:set>
							<jsp:include page="song.jsp"></jsp:include>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>