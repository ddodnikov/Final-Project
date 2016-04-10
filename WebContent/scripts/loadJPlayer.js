var scriptName = document.currentScript;
var trackId = scriptName.dataset.id;
var trackTitle = scriptName.dataset.title;
var trackUrl = scriptName.dataset.url;

	$("#jquery_jplayer_" + trackId).jPlayer({
		ready : function() {
			$(this).jPlayer("setMedia", {
				title : trackTitle,
				mp3 : trackUrl
			});
		},
		play: function() { // To avoid multiple jPlayers playing together.
			$(this).jPlayer("pauseOthers");
		},
		cssSelectorAncestor : "#jp_container_" + trackId,
		swfPath : "/scripts",
		supplied : "mp3",
		useStateClassSkin : true,
		autoBlur : false,
		smoothPlayBar : true,
		keyEnabled : true,
		remainingDuration : true,
		toggleDuration : true
	});
