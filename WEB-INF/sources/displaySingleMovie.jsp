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
		<style type="text/css">
			body{
				text-align: center;
			}
			td {
                                text-align: left;
                        }
			table{
				margin: auto;
				display:inline-block;
			}
			img{
				display:inline;
				float: left;
				margin-left:300px;
			}
		
		</style>
	</head>
	<body>
		<h1>Single Movie Page: </h1>

		<form ACTION="/fabflix/showStars"><input type="SUBMIT" value = "goHome" name = "home"> </form> <br>
		<%
			List<Movie> mlist =  (List<Movie>)session.getAttribute("singleMovieResult");
			if(mlist == null) {
				out.println("<H1> Null MovieList</H1>");
				mlist = new ArrayList<Movie>();
			}
			if(mlist.size() == 0)
				out.println("<H1>NO RESULT</H1>");
			else{
			
		%>
		
		<%	for(Movie m : mlist){
				out.println("<table><tbody>");
				out.println("<tr><td>ID:</td><td>"+ m.id + "</td></tr>");
				out.println("<tr><td>Title:</td><td>"+ m.title + "</td></tr>");
				out.println("<tr><td>Year:</td><td>" + m.year + "</td></tr>");
				out.println("<tr><td>Stars:</td><td>");
		 %>
		<% 			for(Star s : m.starInfo){
					out.println(s.name);%>
										
				<form ACTION="/fabflix/showStars"><input type="SUBMIT" value = "<%=s.id%>"name = "who"> (click id to know more about the star)</form>
		<%
				}
				
				out.println("</td></tr>");
				out.println("<tr><td>Director:</td><td>" + m.director + "</td></tr>");	
		%>
			<tr><td>Trailer:</td><td><a href="<%=m.trailer_url%>"><%=m.trailer_url%></a></td></tr>
			<tr><td>Purchase:</td><td><form ACTION="/fabflix/shoppingCart"><input type="SUBMIT" value = "<%=m.id%>" name = "MovieId"> (click id to add to cart and check out)</form></td></tr>	
			</tbody></table>
			<img src = "<%=m.banner_url%>" width="120" height="120"><br>	
		<%
				out.println("<br><br>");
				break;
			}
			}
			
			session.setAttribute("singleMovieResult",null);
		%>



	</body>

</html>




