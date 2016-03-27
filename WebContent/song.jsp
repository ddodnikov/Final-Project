<%@page import="com.soundcloud.model.Track" %>
<img src="<%= ((Track)request.getAttribute("track")).getTrackURL() %>" />
<p> Title : <%= ((Track)request.getAttribute("track")).getTitle() %> </p>
<p> Ganre : <%= ((Track)request.getAttribute("track")).getGanre() %> </p>
<p> Description : <%= ((Track)request.getAttribute("track")).getDescription() %> </p>
<p> Number of plays : <%= ((Track)request.getAttribute("track")).getNumberOfPlays() %> </p>
<p> Number of likes : <%= ((Track)request.getAttribute("track")).getNumberOfLikes() %> </p>
<p> Date added : <%= ((Track)request.getAttribute("track")).getDateAdded() %> </p>
<br>