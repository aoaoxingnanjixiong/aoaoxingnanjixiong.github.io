<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.*" %>
<%@ page import="Model.*" %>

<!DOCTYPE html>
<html>
	<head>
		<title>movielist</title>
	</head>
	<body>
		<h1>Search Result: </h1>

		<form ACTION="/fabflix/browsingbygenre"><input type="SUBMIT" value="title" name = "order">	
		<form ACTION="/fabflix/browsingbygenre"><input type="SUBMIT" value="year" name = "order">	


		<form ACTION = "/fabflix/browsingbygenre" METHOD = "GET"> 

<input type="SUBMIT" name = "nextprev" id="prevbutton" value = "prev">
<input type="SUBMIT" name = "nextprev" id="nextbutton" value = "next" >
<input type="SUBMIT" name = "ipp" id="ippbutton5" value = "5" >
<input type="SUBMIT" name = "ipp" id="ippbutton10" value = "10">
<input type="SUBMIT" name = "ipp" id="ippbutton15" value = "15">
<input type="SUBMIT" name = "ipp" id="ippbutton20" value = "20">

<input type="SUBMIT" name = "ipp" id="ippbutton25" value = "25">


</form>

<form ACTION = "/fabflix/browsingbygenre" METHOD = "GET">
<input type="SUBMIT" name = "goBack" id="backButton" value = "Back to Previous Page">
</form>

<form ACTION = "/fabflix/browsingbygenre" METHOD = "GET">
<input type="SUBMIT" name = "goBackHome" id="backButton" value = "Back to Home Page">
</form>

<script>
	
//		document.getElementById("prevbutton").disabled = true;
	

</script>
		<%
			List<Movie> mlist =  (List<Movie>)session.getAttribute("searchResult");
			if(mlist == null) {
				out.println("<H1> Null MovieList</H1>");
				mlist = new ArrayList<Movie>();
			}
			if(mlist.size() == 0)
				out.println("<H1>NO RESULT</H1>");
			else{
			
			out.println("size: " + mlist.size());
			for(Movie m : mlist){
				out.println("movie id: " + m.id + "<br>");
				out.println("movie title: " + m.title + "<br>");
				out.println("movie year: " + m.year + "<br>");
				out.println("Stars: <br> ");
	out.println("Size: " + m.starInfo.size() + "<br>"); %>
		<% 			for(Star s : m.starInfo){
					out.println("**** name: " + s.name);%>
										
				<form ACTION="/fabflix/showStars"><input type="SUBMIT" value = "<%=s.id%>"name = "who"> (click id to know more about the star)</form> <br>
		<%
				}
               
		
				out.println("<br>movie director: " + m.director + "<br>");
		%>		
				<img src = "<%=m.banner_url%>" width="120" height="120">
		<%
				out.println("<br><br>");
			}
			}
			
			session.setAttribute("searchResult",null);
		%>



	</body>

</html>




