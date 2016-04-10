<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.soundcloud.model.UserDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="styles/song.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="styles/audioPlayer.css" />

<script type="text/javascript">
	function like(id) {
		$.get("./LikeTrack?like="+id);
		document.getElementById('button'+id).style.visibility = 'hidden';
		document.getElementById('p'+id).style.visibility = 'hidden';
		document.getElementById(id).style.visibility = 'visible';
	}
	
	function unlike(id) {
		$.get("./LikeTrack?unlike="+id);
		document.getElementById('button'+id).style.visibility = 'hidden';
		document.getElementById('p'+id).style.visibility = 'hidden';
		document.getElementById(id).style.visibility = 'visible';
	}
	
	function addToPlaylist(id1, id2) {
		$.get("./addToPlaylist?trackId=" + id1 + "&playlistId=" + id2);
		var buttons = document.getElementsByClassName('playlist'+id1);
		var i;
		for (i = 0; i < buttons.length; i++) { 
		    buttons[i].style.visibility = 'hidden';
		}
	}
	
	function showPlaylists(id) {
		var buttons = document.getElementsByClassName('playlist'+id);
		var i;
		for (i = 0; i < buttons.length; i++) { 
		    buttons[i].style.visibility = 'visible';
		}
	}
</script>
<div class="player">
	<div class="plays">
		<div class="img">
			<img src="./FetchPicture?imageId=${track.imageID}"
				alt="Servlet - Track picture not found" />
		</div>
		<div class="main_player">
			<div class="top">
				<h1 style="color: black;">${track.title}</h1>
			</div>
			<audio controls preload="none">
				<source src="./FetchTrack?trackId=${track.id}" type="audio/mpeg" />
			</audio>
			<div id="likes">
				<c:if test="${not empty sessionScope.currentUser}">
					<c:if test="${track.isLikedByUser == false}">
						<p id="p${track.id}">You haven't liked this track</p>
						<button style="color: black" id="button${track.id}"
							onclick="like(${track.id})">Like</button>
						<p style="color: black; visibility: hidden" id="${track.id}">You
							liked this track</p>
					</c:if>
	
					<c:if test="${track.isLikedByUser == true}">
						<p style="color: black" id="p${track.id}">You like this track</p>
						<button style="color: black" id="button${track.id}"
							onclick="unlike(${track.id})">Unlike</button>
						<p style="color: black; visibility: hidden" id="${track.id}">You unliked this track</p>
					</c:if>
	
					<button style="color: black" id="addToPlaylist${track.id}"
						onclick="showPlaylists(${track.id})">Add to Playlist</button>
	
					<c:forEach var="playlist" items="${sessionScope.playlistsToDisplay}">
						<c:set var="playlist" value="${playlist}" scope="request"></c:set>
						<button style="color: black; visibility: hidden"
							class="playlist${track.id}"
							onclick="addToPlaylist(${track.id},${playlist.id})">${playlist.title}</button>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
	<div id="details">
		<p>Genre: ${track.ganre}</p>
		<p>Number of plays: ${track.numberOfPlays}</p>
		<p>Date added: ${track.dateAdded}</p>
		<form action="./index" method="get">
			<p>
				Uploaded by: <a type="submit"
					href="./home?showUserId=${track.user.id}">${track.user.displayName}</a>
			</p>
		</form>
	</div>
	<div class="likes">
		<div class="top">
			<a href="javascript:;" class='thumbsUp'> <img
				src="images/thumbUp.png" alt="" />
				<div class="thumbsU">${track.numberOfLikes}</div>
			</a>
		</div>

	</div>
</div>
