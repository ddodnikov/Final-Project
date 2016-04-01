<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<div id="userProfile">
	<c:choose>
		<c:when test="${not empty sessionScope.head}">
			<img src="./ShowFetchHeader" alt="Header pic not found" id="headerPic" />
		</c:when>
		<c:otherwise>
			<img src="./images/defaultHeaderPic.jpg" id="headerPic" />
		</c:otherwise>
	</c:choose>

	<div id="infoBlock">
		<c:choose>
			<c:when test="${not empty sessionScope.pic}">
				<img src="./ShowFetchPicture" alt="Profile pic not found" id="profilePic" />
			</c:when>
			<c:otherwise>
				<img src="./images/defaultProfilePic.jpg" id="headerPic" />
			</c:otherwise>
		</c:choose>		
		<div>
			<p id="names">${sessionScope.firstNAME}
				${sessionScope.lastNAME}</p>
			<p class="info">${sessionScope.cityNAME}</p>
			<p class="info">${sessionScope.countryNAME}</p>
		</div>
	</div>
</div>