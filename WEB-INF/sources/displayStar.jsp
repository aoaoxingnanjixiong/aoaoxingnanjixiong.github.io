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
		<title>StarInfo</title>
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
		}
		</style>
	</head>
	<body>
		<h1>Display Star: </h1>
		<form ACTION="/fabflix/searchtitle" METHOD = "GET"><input type="SUBMIT" name = "goHome" value = "homePage"></form> <br>
		<%
			StarInfo starinfo =  (StarInfo)session.getAttribute("searchStars");
			if(starinfo == null) {
				out.println("<H1>NO RESULT</H1>");
			}
			else{
				out.println("<table><tbody>");
				out.println("<tr><td>ID:</td><td>"+ starinfo.id + "</td></tr>");
				out.println("<tr><td>Name:</td><td>" + starinfo.name + "</td></tr>");
				out.println("<tr><td>Date of Birth:</td><td>" + starinfo.date + "</td></tr>");
				out.println("<tr><td>Starred in:</td><td>");
				for(SimpleMovie s : starinfo.moviebystar){
					out.println(s.title);
		%>
				<form ACTION="/fabflix/searchtitle" METHOD = "GET"><input type="SUBMIT" value = "<%=s.title%>"name = "mtitle"></form> 

		<%		
		
				}
		%>
				</td><tr>
		<%              
			}
			session.setAttribute("searchStars",null);
		%>

	</tbody></table>
	<img src = "<%=starinfo.photo_url%>" width="120" height="120"><br>

	</body>

</html>




