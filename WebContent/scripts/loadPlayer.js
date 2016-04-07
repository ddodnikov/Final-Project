(function($) {
	$(window).load(function() {
		/*CHECK IF AUDIO TAG IS SUPPORTED-----------------------------------*/
		var a = document.createElement('audio');
		var supported = !!(a.canPlayType && a.canPlayType('audio/mpeg;').replace(/no/, ''));
		var tracks = new Array();
		var cur_track = 0;
		//Loop through li elements and get the url from data-src attribute
		$('.srcs li').each(function() {
			tracks.push($(this).attr('data-src'));
		})
		$('audio').attr('src', tracks[cur_track]).load();
		//if audio is supported
		if (supported) {
			var player = document.getElementById('audioplayer')
			var volume;
			//give the audio tag src time to change and then trigger play
			setTimeout(function() {
				$('.play').trigger('click'), 1000
			})
			/*PLAY PLAYER*/
			$('body').on('click', '.play', function() {
				$(this).addClass('pause')
				$(this).removeClass('play')
				player.play();
			})
			/*PAUSE PLAYER*/
			$('body').on('click', '.pause', function() {
				$(this).removeClass('pause')
				$(this).addClass('play')
				player.pause();
			})
			/*CHANGE VOLUME*/
			$('.mute').on('click', function() {
				//get the current volume
				var currentVol = player.volume;
				//make the current volume a number
				currentVol = parseFloat(currentVol);
				//check if muted
				if (currentVol == 0) {
					//if yes set volume to 1
					volume = 1;
				} else {
					//if not mute volume
					volume = 0;
				}
				//update player volume to take effect		
				player.volume = volume.toFixed(1);
				})
				//Format the aduio tag's time stamp
				function formatTime(seconds) {
					minutes = Math.floor(seconds / 60);
					minutes = (minutes >= 10) ? minutes : "" + minutes;
					seconds = Math.floor(seconds % 60);
					seconds = (seconds >= 10) ? seconds : "0" + seconds;
					return minutes + ":" + seconds;
				}

				/*UPDATE PROGRESS BAR
				===========================*/
				function updateProgress() {
					var progress = $("#progress");
					var value = 0;

					//If duration = infinity set value to 100
					if (player.duration == 'Infinity') {
						value = 100;
					}
					//else if it is > 0 calculate percentage to highlight
					else if (player.currentTime > 0) {
						value = Math
								.floor((100 / player.duration)
										* player.currentTime);
					}

					//if the value is less than 0 then set it to 0 (to offset the timestamp)
					if (value < 0) {
						value = 0
					}

					//set the width of te progress bar
					progress.stop().css({ 'width' : value + '%' })
					//set the new timestamp
					$('.time').html(formatTime(player.currentTime))
				}

				// add event listener for audio time updates
				player.addEventListener("timeupdate", updateProgress, false);
			}

			// if audio tag is not supported
			else {
				$('.player').hide();
				$('.message').show();

				$('.message a.stream').attr('href', tracks[0]);
			}

			/*CLOSE WINDOW LOAD*/
		})
	})(jQuery)