<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<% 
	if(request.getSession().getAttribute("currentUser") == null)
		response.sendRedirect("./login.jsp");
%>
<div id="userProfile">
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser}">
			<c:choose>
				<c:when test="${sessionScope.currentUser.headerImageID > 0}">
					<img src="./FetchPicture?imageId=${sessionScope.currentUser.headerImageID}" alt="Header pic not found" id="headerPic" />
				</c:when>
				<c:otherwise>
					<img src="./images/defaultHeaderPic.jpg" id="headerPic" />
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>

	<div id="infoBlock">
		<c:choose>
			<c:when test="${not empty sessionScope.currentUser}">
				<c:choose>
					<c:when test="${sessionScope.currentUser.userImageID > 0}">
						<img src="./FetchPicture?imageId=${sessionScope.currentUser.userImageID}" alt="Profile pic not found" id="headerPic" />
					</c:when>
					<c:otherwise>
						<img src="./images/defaultProfilePic.jpg" id="headerPic" />
					</c:otherwise>
				</c:choose>
			</c:when>
		</c:choose>		
		<div>
			<p id="names">${sessionScope.currentUser.firstName}
				${sessionScope.currentUser.lastName}</p>
			<p class="info">${sessionScope.currentUser.city}</p>
			<p class="info">${sessionScope.currentUser.country}</p>
		</div>
	</div>
</div>