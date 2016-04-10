$(document).ready(function likeSong(id) {
	$("#displayLike" + id).click(function() {
		$.ajax({ // create an ajax request to load_page.php
			type : "GET",
			url : "UpdateLikesForTrack?trackId=" + id,
			cache: false,
			dataType : "html", // expect html to be returned
			success : function(response) {
				$("#thumbsU").html(response);
			},
			error : function() {
				console.log("error");
			}
		});
	});
});