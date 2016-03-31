<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<div id="userProfile">
	<c:choose>
		<c:when test="${not empty sessionScope.currentHeaderPic}">
			<img src="./FetchHeader" alt="Header pic not found" id="headerPic" />
		</c:when>
		<c:otherwise>
			<img src="./images/defaultHeaderPic.jpg" id="headerPic" />
		</c:otherwise>
	</c:choose>

	<div id="infoBlock">
		<c:choose>
			<c:when test="${not empty sessionScope.currentProfilePic}">
				<img src="./FetchPicture" alt="Profile pic not found" id="profilePic" />
			</c:when>
			<c:otherwise>
				<img src="./images/defaultProfilePic.jpg" id="headerPic" />
			</c:otherwise>
		</c:choose>		
		<div>
			<p id="names">${sessionScope.currentUser.firstName}
				${sessionScope.currentUser.lastName}</p>
			<p class="info">${sessionScope.currentUser.city}</p>
			<p class="info">${sessionScope.currentUser.country}</p>
		</div>
	</div>
</div>