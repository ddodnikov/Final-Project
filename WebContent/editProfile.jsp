<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/editProfile.css" />
<title>Edit your profile :: SoundCloud</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<div id="wrapper">
	<h1>Edit your profile</h1>
	<form method="post" action="./EditProfile">
		<div id="left">
			<label for="firstName">First Name:</label>
			<input name="firstName" type="text" placeholder="Enter your first name" />
			<label for="lastName">Last Name:</label>
			<input type="text" name="lastName" placeholder="Enter your last name" />
			<label for="city">City:</label>
			<input type="text" name="city" placeholder="Enter your city" />
			<label for="country">Country:</label>
			<input type="text" name="country" placeholder="Enter your country" />
			<label for="biography">Biography:</label><br />
			<textarea name="biography" rows=5 cols=30></textarea>
		</div>
		<div id="right">
			<div id="personalPics">
				<label for="uploadProfilePic">Profile picture:</label>
				<input name="uploadProfilePic" type="file" />
				<label for="uploadHeaderPic">Header picture:</label>
				<input name="uploadHeaderPic" type="file" />
			</div>
			<input type="submit" value="Update">
		</div>
	</form>	
	</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>