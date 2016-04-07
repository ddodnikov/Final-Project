<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.soundcloud.model.UserDAO"%>
<%@page import="com.soundcloud.model.Track" %>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="styles/song.css" rel="stylesheet" />

<div id="songContainer">
	<div>
		<c:catch var="catchException">
		   <img src="./FetchPlaylistImage?playlistId=${playlist.id}" alt="Playlist image not found" />
		</c:catch>
		<c:if test="${catchException} != null">
			<img src="images/defaultSongImage.jpg" alt="Image not found" />
		</c:if>
	</div>
	<div id="details">
		<p>Title: <a type="submit" href="./playlists?showPlaylistId=${playlist.id}">${playlist.title}</a></p>
		<p>Number of tracks: ${playlist.numberOfTracks}</p>
		<form action="./home" method="get">
			<p>
				User: <a type="submit" href="./home?showUserId=${playlist.user.id}">${playlist.user.displayName}</a>
			</p>
		</form>
	</div>
</div>