<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.soundcloud.model.UserDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<link type="text/css" href="styles/song.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="styles/audioPlayer.css" />

<script type="text/javascript">
	function addToPlaylist(playlistId, trackId) {
		$.ajax({
			url : "AddToPlaylist?playlistId=" + playlistId + "&trackId=" + trackId,
			type : "GET",
			cache : false,
			dataType : "html",
			success : function() {
				console.log("success");
				$("#li" + playlistId + "-" + trackId).css("display", "none");
			},
			error : function() {
				console.log("error");
			}
		})
	}


	function likeSong(id) {
		$.ajax({ // create an ajax GET request to LikeTrack servlet
			type : "GET",
			url : "LikeTrack?like=" + id + "&trackId=" + id,
			cache: false,
			dataType : "html", // expect html to be returned
			success : function(response) {
				$("#thumbs" + id).html(response);
				$(".thumbsUp#like" + id).attr("disabled", true);
				$(".thumbsUp#like" + id).css("opacity", 0.5);
				$(".thumbsDown#unlike" + id).attr("disabled", false);
				$(".thumbsDown#unlike" + id).css("opacity", 1);
			},
			error : function() {
				console.log("error");
			}
		});
	};

	function unlikeSong(id) {
		$.ajax({
			type : "GET",
			url : "LikeTrack?unlike=" + id + "&trackId=" + id,
			dataType : "html", // expect html to be returned
			cache : false,
			success : function(response) {
				$("#thumbs" + id).html(response);
				$("#unlike" + id).attr("disabled", true);
				$("#unlike" + id).css("opacity", 0.5);
				$("#like" + id).attr("disabled", false);
				$("#like" + id).css("opacity", 1);
			},
			error : function() {
				console.log("error");
			}
		});
	};
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
			<audio controls preload="none" onended="callServlet(${track.id})">
				<source src="./FetchTrack?trackId=${track.id}" type="audio/mpeg" />
			</audio>
			<script type="text/javascript">
				function callServlet(id) {
					$.ajax({
						url : "CountPlayOfTrack?trackId=" + id,
						type : 'get'
					})
			    }
			</script>
			<div id="likes">
				<c:if test="${not empty sessionScope.currentUser}">
					<span class="dropdown">
						<a class="dropdown-toggle playlistDropdown" data-toggle="dropdown">Add to playlist <span class="caret"></span></a>
						<div class="dropdown-menu playlistSelect">
							<ul>
								<li><a href="./CreatePlaylist">Create playlist</a></li>
								<c:forEach var="playlist" items="${sessionScope.playlistsToDisplay}">
									<c:set var="playlist" value="${playlist}" scope="request"></c:set>
									<script>
										$.ajax({
											url : "IsTrackInPlaylist?playlistId=${playlist.id}&trackId=${track.id}",
											type : "get",
											dataType : "html",
											cache : false,
											success : function(response) {
												if (response == "trackExists") { // the track exists in this playlist
													// hide the playlist list item - the user can't add the track to the same playlist twice
													$("#li${playlist.id}-${track.id}").css("display", "none");
												}
											},
											error : function() {
												console.log("Cannot retrieve data from servlet.");
											}
										})
									</script>
									<li id="li${playlist.id}-${track.id}">
										<a class="playlist${track.id}" onclick="addToPlaylist(${playlist.id},${track.id})">${playlist.title}</a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</span>
				</c:if>
			</div>
		</div>
	</div>
	<div id="details">
		<p>Genre: ${track.ganre}</p>
		<p>Number of plays: ${track.numberOfPlays}</p>
		<p>Date added: <fmt:formatDate type="both" value="${track.dateAdded}" /></p>
		<form action="./index" method="get">
			<p>
				Uploaded by: <a type="submit"
					href="./home?showUserId=${track.user.id}">${track.user.displayName}</a>
			</p>
		</form>
	</div>
	<c:if test="${not empty sessionScope.currentUser}">
		<div class="likes">
		<script type="text/javascript">
			$.ajax({
				url : "IsTrackLikedByUser?trackId=${track.id}",
				type : "get",
				dataType : "html", // expect html to be returned
				cache : false,
				success : function(response) {
					if (response == "trackIsLiked") { // the track is liked by the current user
						// disable like button
						$("#like${track.id}").attr("disabled", true);
						$("#like${track.id}").css("opacity", 0.5);
					} else if (response == "trackIsUnliked") {
						// disable unlike button
						$("#unlike${track.id}").attr("disabled", true);
						$("#unlike${track.id}").css("opacity", 0.5);
					}
				},
				error : function() {
					console.log("error");
				}
			});
		</script>
			<div class="top">
				<button class='thumbsUp' id="like${track.id}" onclick="likeSong(${track.id})">
					<img src="images/thumbUp.png" alt="" />
				</button>
			</div>
			<div class="thumbsU" id="thumbs${track.id}">${track.numberOfLikes}</div>
			<div class="bottom">
				<button class='thumbsDown' id="unlike${track.id}" onclick="unlikeSong(${track.id})">
					<img src="images/thumbDown.png" alt="" />
				</button>
			</div>
		</div>
	</c:if>
</div>
<script src="scripts/playOneSongAtATime.js"></script>