<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/uploadSong.css" />
<link type="image/x-icon" rel="icon" href="images/siteIcon.ico" />
<title>Home :: SoundCloud</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<h1>Upload track</h1>
			<form method="post" action="./UploadSong" enctype="multipart/form-data">
				<div id="left">
					<input type="file" name="songUpload" />
					<label for="title">Title(required)</label> 
					<input type="text" name="title" placeholder="Name your track" />
					<label for="genre">Genre</label>
					<select name="genre" type="text" >
						 <option selected="selected" value="Other">Other</option>
 						 <option value="Alternative Rock">Alternative Rock</option>
 						 <option value="Ambient">Ambient</option>
 						 <option value="Classical">Classical</option>
  						 <option value="Country">Country</option>
  						 <option value="Dance & EDM">Dance & EDM</option>
 						 <option value="Dancehall">Dancehall</option>
 						 <option value="Deep House">Deep House</option>
 						 <option value="Drum & Bass">Drum & Bass</option>
  						 <option value="Dubstep">Dubstep</option>
  						 <option value="Electronic">Electronic</option>
 						 <option value="Folk & Singer-Songwriter">Folk & Singer-Songwriter</option>
 						 <option value="Hip-hop & Rap">Hip-hop & Rap</option>
 						 <option value="House">House</option>
  						 <option value="Indie">Indie</option>
  						 <option value="Jazz & Blues">Jazz & Blues</option>
 						 <option value="Latin">Latin</option>
 						 <option value="Metal">Metal</option>
  						 <option value="Piano">Piano</option>
  						 <option value="Pop">Pop</option>
 						 <option value="R&B & Soul">R&B & Soul</option>
 						 <option value="Reggae">Reggae</option>
 						 <option value="Reggeaton">Reggeaton</option>
  						 <option value="Rock">Rock</option>
  						 <option value="Soundtrack">Soundtrack</option>
 						 <option value="Techno">Techno</option>
 						 <option value="Trance">Trance</option>
 						 <option value="Trap">Trap</option>
  						 <option value="Triphop">Triphop</option>
  						 <option value="World">World</option>
					</select>
					<label for="tags">Additional tags</label> 
					<input type="text" name="tags" placeholder="Add tags to describe the genre and mood of your track" /> 
					<label for="description">Description</label> <br>
					<textarea name="description" rows=5 cols=45 placeholder="Describe your track"></textarea>
				</div>
				<div id="right">
					<div id="trackPic">
						<label for="currentProfile">Track picture:</label> 
						<img src="${pageContext.request.contextPath}/userProfilePics/<c:out value="${currentProfilePic}"></c:out>" />
						<input name="uploadTrackPic" type="file" /> 
					</div>
					<input type="submit" value="Upload">
				</div>
				<c:if test="${not empty songErrorMessage}">
					<br>
					<p style="color: red">${songErrorMessage}</p>
				</c:if>
			</form>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>