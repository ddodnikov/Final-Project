<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	if (request.getSession().getAttribute("currentUser") == null) {
		response.sendRedirect("./login.jsp");
	}
%>
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
						<label for="displayName">Display name:</label> 
						<input name="displayName" value="${sessionScope.currentUser.displayName}" type="text" placeholder="Enter new display name" required/>
						<label for="firstName">First name:</label> 
						<input name="firstName" value="${sessionScope.currentUser.firstName}" type="text" placeholder="Enter your first name"/> 
						<label for="lastName">Last name:</label> 
						<input type="text" name="lastName" value="${sessionScope.currentUser.lastName}" placeholder="Enter your last name" />
					</div>
					<div class="rightColumn">
						<label for="city">City:</label> 
						<input type="text" name="city" value="${sessionScope.currentUser.city}" placeholder="Enter your city"/> 
						<label for="country">Country:</label>
						<input type="text" name="country" value="${sessionScope.currentUser.country}" placeholder="Enter your country"/>
						<label for="biography">Biography:</label><br />
						<textarea name="biography" rows=5 cols=30>${sessionScope.currentUser.biography}</textarea>
					</div>
				</div>
				<div id="bottom">
					<div class="leftColumn">
						<label for="currentProfile">Profile picture:</label>
						<c:choose>
							<c:when test="${not empty sessionScope.currentUser}">
								<c:choose>
									<c:when test="${sessionScope.currentUser.userImageID > 0}">
										<img src="./FetchPicture?imageId=${sessionScope.currentUser.userImageID}" alt="Profile pic not found" id="outImage" />
									</c:when>
									<c:otherwise>
										<img src="./images/defaultProfilePic.jpg" id="outImage" />
									</c:otherwise>
								</c:choose>
							</c:when>
						</c:choose>	
						<label for="uploadProfilePic">Update profile picture:</label> <input
							name="uploadProfilePic" type="file" id="profilePicField" accept="image/jpeg"/>
						<script src="scripts/dynamicProfilePicLoad.js"></script>
					</div>
					<div class="rightColumn">
						<label for="currentHeader">Header picture:</label>
						<c:choose>
							<c:when test="${not empty sessionScope.currentUser}">
								<c:choose>
									<c:when test="${sessionScope.currentUser.headerImageID > 0}">
										<img src="./FetchPicture?imageId=${sessionScope.currentUser.headerImageID}" alt="Header pic not found" id="outHeader" />
									</c:when>
									<c:otherwise>
										<img src="./images/defaultHeaderPic.jpg" id="outHeader" />
									</c:otherwise>
								</c:choose>
							</c:when>
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