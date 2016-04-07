<%@page import="java.util.List"%>
<%@page import="com.soundcloud.model.GenreDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="scripts/tagManager.js"></script>
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/uploadSong.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<script>
	$(function() {
		$('input:text').keypress(function(e) {
			var code = e.keyCode || e.which;
			if (code == 44) {
				$.ajax({
					url : 'TagsServlet',
					data : {
						"currentTag" : $("#tags input:text").text(),
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
		});
	});
</script>
<title>Home :: SoundCloud</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
		<div id="tags">
			<input type="text" value="" placeholder="Add tags" />
		</div>
		<input type="submit" value="probvai" />
	</form>
</body>
</html>