<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
 $("#tablediv").hide();
     $("#showTable").click(function(event){
           $.get('PopulateTable',function(responseJson) {
            if(responseJson!=null){
                $("#tracktable").find("tr:gt(0)").remove();
                var table1 = $("#tracktable");
                $.each(responseJson, function(key,value) { 
                     var rowNew = $("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
                        rowNew.children().eq(0).text(value['genre_id']);
                        rowNew.children().eq(1).text(value['title']); 
                        rowNew.children().eq(2).text(value['description']); 
                        rowNew.children().eq(3).text(value['track_uri']);
                        rowNew.children().eq(4).text(value['likes_count']); 
                        rowNew.children().eq(5).text(value['plays_count']);
                        rowNew.children().eq(6).text(value['date_added']); 
                        rowNew.appendTo(table1);
                });
                }
            });
            $("#tablediv").show();          
  });      
});
</script>
<title>Test pagination</title>
</head>
<body class="container">
<h1>AJAX Retrieve Data from Database in Servlet and JSP using JSONArray</h1>
<input type="button" value="Show Table" id="showTable"/>
<div id="tablediv">
<table cellspacing="0" id="tracktable">
	<tr>
		<th scope="col">Genre</th>
		<th scope="col">Title</th>
		<th scope="col">Description</th>
		<th scope="col">Track URI</th>
		<th scope="col">Likes Count</th>
		<th scope="col">Plays Count</th>
		<th scope="col">Date added</th>
	</tr> 
</table>
</div>
</body>
</html>