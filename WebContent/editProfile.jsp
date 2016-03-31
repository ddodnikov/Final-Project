<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/editProfile.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>Edit your profile :: SoundCloud</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<h1>Edit your profile</h1>

			<form method="post" action="./EditProfile"
				enctype="multipart/form-data">
				<div id="top">
					<div class="leftColumn">
						<label for="firstName">First name:</label> <input name="firstName"
							type="text" placeholder="Enter your first name" /> <label
							for="lastName">Last name:</label> <input type="text"
							name="lastName" placeholder="Enter your last name" />
					</div>
					<div class="rightColumn">
						<label for="city">City:</label> <input type="text" name="city"
							placeholder="Enter your city" /> <label for="country">Country:</label>
						<input type="text" name="country" placeholder="Enter your country" />
						<label for="biography">Biography:</label><br />
						<textarea name="biography" rows=5 cols=30></textarea>
					</div>
				</div>
				<div id="bottom">
					<div class="leftColumn">
						<label for="currentProfile">Profile picture:</label>
						<c:choose>
							<c:when test="${not empty sessionScope.currentProfilePic}">
								<img src="./FetchPicture" alt="Not found" id="outImage" />
							</c:when>
							<c:otherwise>
								<img src="./images/defaultProfilePic.jpg" id="outImage" />
							</c:otherwise>
						</c:choose>
						<label for="uploadProfilePic">Update profile picture:</label> <input
							name="uploadProfilePic" type="file" id="profilePicField" accept="image/jpeg"/>
						<script src="scripts/dynamicProfilePicLoad.js"></script>
					</div>
					<div class="rightColumn">
						<label for="currentHeader">Header picture:</label>
						<c:choose>
							<c:when test="${not empty sessionScope.currentHeaderPic}">
								<img src="./FetchHeader" alt="Not found" id="outHeader" />
							</c:when>
							<c:otherwise>
								<img src="./images/defaultHeaderPic.jpg" id="outHeader" />
							</c:otherwise>
						</c:choose>
						<label for="uploadHeaderPic">Update header picture:</label> <input
							name="uploadHeaderPic" type="file" id="headerPicField" accept="image/jpeg" />
						<script src="scripts/dynamicHeaderPicLoad.js"></script>
					</div>
				</div>
				<input type="submit" value="Update">
			</form>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>