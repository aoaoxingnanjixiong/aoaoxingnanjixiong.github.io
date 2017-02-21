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

public class ParsingReportsServlet extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }
   public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
                PrintWriter out = response.getWriter();
                 RequestDispatcher r = request.getRequestDispatcher("/parsingreport.txt");

                r.forward(request, response);
                return;
        }


}

