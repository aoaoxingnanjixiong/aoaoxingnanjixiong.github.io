import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Employee extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }
   public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
		PrintWriter out = response.getWriter();
		if(request.getParameter("UIProject3") != null){
                 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/UI.html");

                r.forward(request, response);
                return;
        }

		try{
              String nextPage="";
	      nextPage = "/_dashboard.html";
		try{
              RequestDispatcher rd = request.getRequestDispatcher(nextPage);
              rd.forward(request, response);
        } catch (ServletException se) {
        out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "RequestDispatcher: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>error in doPost: " +
                             "</P></BODY></HTML>");
                return;

        }
	}catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();

	}
}


