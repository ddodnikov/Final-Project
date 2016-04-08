$(function() { // DOM ready
	// ::: TAGS BOX
	$("#tags input").on({
		focusout : function() {
			var txt = this.value.replace(/[^a-z0-9\+\-\.\#]/ig, ''); // allowed characters
			if (txt)
				$("<span/>", {
					text : txt.toLowerCase(),
					insertBefore : this
				});
			this.value = "";
		},
		keypress : function(e) {
			var code = e.keyCode || e.which;
			if (code == 44 || code == 32 || code == 188) { // on press comma, space
				console.log($('#tags input:text').val());
				$.ajax({
					url : 'TagsServlet',
					data : {
						currentTag : $('#tags input:text').val(),
					},
					type : 'put',
					cache : false,
					success : function(data) {
						console.log(data);
					},
					error : function() {
						console.log('error');
					}
				});
			}
		},
		keyup : function(ev) {
			// if: comma (delimit more keyCodes with | pipe)
			// enter key = code 13
			if (/(32|188|44)/.test(ev.which)) {
				$(this).focusout();				
			}
		}
	});
	$('#tags').on('click', 'span', function() {
		// confirmation for tag delete
		// if (confirm("Remove " + $(this).text() + "?"))
		$(this).remove();
	});
});