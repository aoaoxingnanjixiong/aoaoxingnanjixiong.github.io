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
		<style = text/css>
		body{
			text-align:center;
		}
		</style>
	</head>
	<body>
		<h1>Shopping Cart: </h1>
<br> <form ACTION = "/fabflix/shoppingCart">
        <input type="SUBMIT" name = "homehome" value = "HomePage">
        </form>

<br>



		<%
			List<MovieCart> mCart =  (List<MovieCart>)session.getAttribute("cartSession");
			if(mCart == null) {
				mCart = new ArrayList<MovieCart>();
			}
			if(mCart.size() == 0)
				out.println("<H1>NO RESULT</H1>");
			else{
			
			for(MovieCart m : mCart){%>
			<form ACTION="/fabflix/shoppingCart" METHOD = "GET">
				Please input the number of this movie in cart:
				<input type="text" name="inputNumber">
				<input type="hidden" name = "movieId" value = "<%=m.id%>">
				<input type="SUBMIT" value="Submit">
			</form>
		<%		out.println("movie id: " + m.id + "<br>");
				out.println("movie title: " + m.title + "<br>");
				out.println("movie count: " + m.count + "<br>");	
			}
               
		
				out.println("<br><br><br>");
			}
			
			
		%>
		<form ACTION="/fabflix/shoppingCart" METHOD = "GET">
			<input type="SUBMIT" value="CheckOut" name = "checkout">
		</form>


	</body>

</html>




