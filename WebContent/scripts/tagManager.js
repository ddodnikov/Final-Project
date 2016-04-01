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
		keyup : function(ev) {
			// if: comma (delimit more keyCodes with | pipe)
			// enter key = code 13
			if (/(32)/.test(ev.which))
				$(this).focusout();
		}
	});
	$('#tags').on('click', 'span', function() {
		// confirmation for tag delete
		// if (confirm("Remove " + $(this).text() + "?"))
		$(this).remove();
	});
});