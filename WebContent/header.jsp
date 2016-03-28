<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<head>
	<link rel="stylesheet" href="styles/bootstrap.min.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<header>
	<nav>
		<ul>
			<li id="logo"><a href="./"><img
					src="images/soundCloudLogo.png" alt="siteLogo" /></a></li>
			<li><a href="#">Charts</a></li>
			<li id="searchBar"><input type="search" results=3
				placeholder="Search tracks" /></li>
			<c:choose>
				<c:when test="${not empty sessionScope.userId}">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown"> Profile <span class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<li><a href="./EditProfile">Edit Profile</a></li>
							<li><a href="./Logout">Log out</a></li>
						</ul></li>
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