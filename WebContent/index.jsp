<%@page import="com.soundcloud.model.TrackDAO"%>
<%@page import="com.soundcloud.model.Track"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/welcome.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>SoundCloud :: IT Talents</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">

				<h1>Welcome to SoundCloud</h1>
				<h2>Hear the world's sounds</h2>
				<h3>Find the music you love. Discover new tracks.</h3>

				<section class="4u 12u(mobile)"> <a href="#"
					class="image full"><img src="images/pic01.jpg" alt="" /></a>
				<div class="box">
					<h4>
						<a href="#">Service</a>
					</h4>
				</div>
				</section>
				<section class="4u 12u(mobile)"> <a href="#"
					class="image full"><img src="images/pic02.jpg" alt="" /></a>
				<div class="box">
					<h4>
						<a href="#">Service</a>
					</h4>
				</div>
				</section>
				<section class="4u 12u(mobile)"> <a href="#"
					class="image full"><img src="images/pic03.jpg" alt="" /></a>
				<div class="box">
					<h4>
						<a href="#">Service</a>
					</h4>
				</div>
				</section>

		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>