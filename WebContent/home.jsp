<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="styles/global.css" />
<link type="text/css" rel="stylesheet" href="styles/register.css" />
<title>Insert title here</title>
</head>
<body>
	<div id="outer">
		<jsp:include page="header.jsp"></jsp:include>
		<div id="wrapper">
			<form action="./UploadSong" method="post" enctype="multipart/form-data">
				Select file to upload: <input type="file" name="fileUpload" size="50" /> 
				<br /> 
				<input type="submit" value="Upload File" />
				
				<% if(request.getAttribute("message") != null) { %>
					<p style="color: red"> <%=  request.getAttribute("message") %>
				<% } %>
				
			</form>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>