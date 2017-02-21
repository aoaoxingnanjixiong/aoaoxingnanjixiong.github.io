
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;

public class ShowStarsServlet extends HttpServlet
{
        public String getServletInfo(){
                return "BrowsingServlet";
        }
        public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	PrintWriter out = response.getWriter();


        if(request.getParameter("home") == null && request.getParameter("who") == null){
		out.println("In Null");
			
		StarInfo searchResults = new StarInfo();
                request.getSession().setAttribute("searchStars", searchResults);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displayStar.jsp");
                rd.forward(request, response);
		out.print("<h1>  Null parameter   </h1>");
                return;
        }
	if(request.getParameter("home") != null){
                RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");
                r.forward(request, response);
		return;
	}

        //String type = "genre";
        StarInfo searchResults= StarInfo.getStar(request.getParameter("who"));
        request.getSession().setAttribute("searchStars", searchResults);
 out.println("who: " + request.getParameter("who"));
out.println("Name: " + searchResults.name + "id:  " + searchResults.id);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displayStar.jsp");



        rd.forward(request, response);
        return;
    }
}
