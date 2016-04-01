<%@page import="com.soundcloud.model.UserDAO"%>
<%@page import="com.soundcloud.model.Track" %>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<p>Title: ${track.title}</p>
<p>Genre: ${track.ganre}</p>
<p>Description: ${track.description}</p>
<p>Number of plays: ${track.numberOfPlays}</p>
<p>Number of likes: ${track.numberOfLikes}</p>
<p>Date added: ${track.dateAdded}</p>
<form action="./index" method="get">
	<p>User: <a type="submit" href="./index?userid=${track.user.id}">${track.user.displayName}</a></p>
</form>
<br>
<audio controls>
	<source src="file:///${track.trackURL}">
</audio>