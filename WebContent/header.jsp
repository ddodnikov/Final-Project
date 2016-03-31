<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<link rel="stylesheet" href="styles/bootstrap.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<header>
	<nav>
		<ul>
			<li id="logo">
			<a href="./Home"><img src="images/soundCloudLogo.png" alt="siteLogo" /></a>
			</li>
			<li>
			<a href="./Charts">Charts</a>
			</li>
			<li id="searchBar">
			<form action="./Search" method="get">
				<input type="search" results=3 placeholder="Search tracks by title, genre or tag" name="search"/>
				<input type="submit" value="Search" style="position: absolute; left: -9999px" />
			</form>
			</li>
			<c:choose>
				<c:when test="${not empty sessionScope.userId}">
					<li class="dropdown">
					<span id="smallUserPic">
						<c:choose>
						<c:when test="${not empty sessionScope.currentProfilePic}">
							<img src="./FetchPicture" />
						</c:when>
						<c:otherwise>
							<img src="./images/defaultProfilePic.jpg" />
						</c:otherwise>
						</c:choose>
						
					</span>
					<a class="dropdown-toggle" data-toggle="dropdown">${sessionScope.currentUser.displayName} <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="./Home">My Home</a></li>
						<li><a href="./EditProfile">Edit Profile</a></li>
						<li><a href="./UploadSong">Upload Song</a></li>
						<li><a href="./Logout">Log out</a></li>
					</ul>
					</li>
				</c:when>
				<c:otherwise>
					<li><a href="./Login">Log in</a></li>
					<li><a href="./Register">Register</a></li>
					<li><a href="./#">About Us</a></li>
				</c:otherwise>
			</c:choose>
			
		</ul>
	</nav>
</header>