<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.soundcloud.model.UserDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" href="styles/song.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="styles/audioPlayer.css"/>

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
</script>
<div class="player">
	<div class="plays">
		<div class="img">
			<img src="./FetchTrackImage?trackId=${track.id}" alt="Servlet - Track picture not found" />
		</div>
		<div class="main_player">
			<audio controls preload="none">
				<source src="./FetchTrack?trackId=${track.id}" type="audio/mpeg" />
			</audio>
		</div>
	</div>
	<div id="details">
		<p>Genre: ${track.ganre}</p>
		<p>Number of plays: ${track.numberOfPlays}</p>
		<p>Date added: ${track.dateAdded}</p>
		<form action="./index" method="get">
			<p>
				Uploaded by: <a type="submit" href="./home?showUserId=${track.user.id}">${track.user.displayName}</a>
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
		<%--<div class="bottom">
			<a href="javascript:;" class='thumbsDown'> <img
				src="images/thumbDown.png" alt="" />
				<div class="thumbsU">86</div>
			</a>
		</div>--%>
	</div>
</div>

<%-- <ul class="srcs">
	<li data-src='./FetchTrack?trackId=${track.id}'></li>
</ul>
<audio id="audioplayer" preload="none">
	<source src="./FetchTrack?trackId=${track.id}" />
</audio>

<script type="text/javascript" src="scripts/loadPlayer.js"></script>--%>
<script src="scripts/playOneSongAtATime.js"></script>
<%-- <div id="songContainer">
	<div>
		<c:catch var="exception">
			<img src="./FetchTrackImage?trackId=${track.id}" alt="Servlet - Track picture not found" />
		</c:catch>
		<c:if test="${ exception != null }">
			<img src="images/defaultSongImage.jpg" alt="Default - Track pic not found" />
		</c:if>
	</div>
	<div id="details">
		<p>Title: ${track.title}</p>
		<p>Genre: ${track.ganre}</p>
		<p>Number of plays: ${track.numberOfPlays}</p>
		<p>Number of likes: ${track.numberOfLikes}</p>
		<p>Date added: ${track.dateAdded}</p>
		<form action="./index" method="get">
			<p>
				User: <a type="submit" href="./home?showUserId=${track.user.id}">${track.user.displayName}</a>
			</p>
		</form>
	</div>
	<c:if test="${not empty sessionScope.currentUser}">
		<c:if test="${track.isLikedByUser == false}">
			<p id="p${track.id}">You don't like this track</p>
			<button id="button${track.id}" onclick="like(${track.id})">Like</button>
			<p id="${track.id}" style="visibility: hidden">You liked this
				track</p>
		</c:if>
		<c:if test="${track.isLikedByUser == true}">
			<p id="p${track.id}">You like this track</p>
			<button id="button${track.id}" onclick="unlike(${track.id})">Unlike</button>
			<p id="${track.id}" style="visibility: hidden">You unliked this
				track</p>
		</c:if>
	</c:if>
	<audio controls preload="none">
		<source src="./FetchTrack?trackId=${track.id}" type="audio/mpeg" />
	</audio>
	<script src="scripts/playOneSongAtATime.js"></script>
</div>
--%>