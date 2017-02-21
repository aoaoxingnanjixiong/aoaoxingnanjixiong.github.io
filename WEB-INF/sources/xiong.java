import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class xiong extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
   	out.println("<HTML><BODY><H1>Xiong Xiong Xiong ~~~</H1></BODY></HTML>");
    }
}

