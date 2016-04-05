<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="com.soundcloud.model.UserDAO"%>
<%@page import="com.soundcloud.model.Track" %>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>

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

<p>Title: ${track.title}</p>
<p>Genre: ${track.ganre}</p>
<p>Description: ${track.description}</p>
<p>Number of plays: ${track.numberOfPlays}</p>
<p>Number of likes: ${track.numberOfLikes}</p>
<p>Date added: ${track.dateAdded}</p>
<form action="./home" method="get">
	<p>User: <a type="submit" href="./home?showUserId=${track.user.id}">${track.user.displayName}</a></p>
</form>

<c:if test="${not empty sessionScope.currentUser}">
	<c:if test="${track.isLikedByUser == false}">
		<p id="p${track.id}">You don't like this track</p>
		<button id="button${track.id}" onclick="like(${track.id})">Like</button>
		<p id="${track.id}" style = "visibility:hidden">You liked this track</p>
	</c:if>
	<c:if test="${track.isLikedByUser == true}">
		<p id="p${track.id}">You like this track</p>
		<button id="button${track.id}" onclick="unlike(${track.id})">Unlike</button>	
		<p id="${track.id}" style = "visibility:hidden">You unliked this track</p>
	</c:if>
</c:if>
	<br>	
<audio controls>
	<source src="file:///${track.trackURL}">
</audio>