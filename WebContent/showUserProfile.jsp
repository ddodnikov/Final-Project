<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<div id="userProfile">
	<c:choose>
		<c:when test="${not empty sessionScope.showUser.headerImageURI}">
			<img src="./FetchHeaderShow" alt="Header pic not found" id="headerPic" />
		</c:when>
		<c:otherwise>
			<img src="./images/defaultHeaderPic.jpg" id="headerPic" />
		</c:otherwise>
	</c:choose>

	<div id="infoBlock">
		<c:choose>
			<c:when test="${not empty sessionScope.showUser.userImageURI}">
				<img src="./FetchPictureShow" alt="Profile pic not found" id="profilePic" />
			</c:when>
			<c:otherwise>
				<img src="./images/defaultProfilePic.jpg" id="headerPic" />
			</c:otherwise>
		</c:choose>		
		<div>
			<p id="names">${sessionScope.showUser.firstName}
				${sessionScope.showUser.lastName}</p>
			<p class="info">${sessionScope.showUser.city}</p>
			<p class="info">${sessionScope.showUser.country}</p>
		</div>
	</div>
</div>