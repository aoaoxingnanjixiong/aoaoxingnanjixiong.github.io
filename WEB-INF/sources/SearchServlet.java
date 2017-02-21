
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SearchServlet extends HttpServlet
{
    public String getServletInfo()
    {
       return "fabflixSearchServlet";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>fabflixSearchServlet</TITLE></HEAD>");
        out.println("<BODY><H1>fabflixSearchServlet</H1>");


        try
           {
	      String nextPage="";
	      String type = request.getParameter("searchpage");
		 if(type.equals("Check Out"))
		 	nextPage = "/WEB-INF/sources/shoppingCart.jsp";
		 else if(type.equals("Browsing by Genre"))
                        nextPage = "/WEB-INF/sources/genre.jsp";
		 else if(type.equals("AdvanceSearch"))
			nextPage = "/WEB-INF/sources/advancesearch.jsp";
		else if(type.equals("AutocompletionSearch"))
                        nextPage = "AjaxSearch.html";
	
		 else
                        nextPage = "/WEB-INF/sources/title.jsp";
	      RequestDispatcher rd = request.getRequestDispatcher(nextPage);
	      rd.forward(request, response);	
            }

        catch(Exception e)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            e.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
    }
}
