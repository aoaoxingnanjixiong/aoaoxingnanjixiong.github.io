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
import java.sql.CallableStatement;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;
public class StoredProcedure extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http POST

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
//	String loginUser = "classta";
//        String loginPasswd = "classta";
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
        out.println("<BODY><H1>MovieDB</H1>");
	response.setContentType("text/html");    // Response mime type
        try{
	      out.println("<H1>In SQL TRY!</H1>");
  //            Class.forName("com.mysql.jdbc.Driver").newInstance();

    //          Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		Context initCtx = new InitialContext();

                Context envCtx = (Context) initCtx.lookup("java:comp/env");
               // Look up our data source

                DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
                Connection dbcon = ds.getConnection();

             out.println("<H1>Doing SQL TRY1!</H1>");
		 // Declare our statement
	      String nextPage = "";
              String title = request.getParameter("inputtitle");
//out.println("<H1>Doing SQL TRY1!</H1>");
              String year = request.getParameter("inputyear");
	      String director = request.getParameter("inputdirector");
	      String banner_url = request.getParameter("inputbanner_url");
	      String trailer_url = request.getParameter("inputtrailer_url");
	      String star_last_name = request.getParameter("input_star_last_name");
	      String genre_name = request.getParameter("inputgenre_name");
//out.println("<H1>Doing SQL TRY3!</H1>");

		if(title.equals("") || year.equals("") || director.equals("")){
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/UI.html");
              rd.forward(request, response);
		}

	      String sqlCall = "{call add_movie(?,?,?,?,?,?,?)}";
//out.println("<H1>Doing SQL TRY5!</H1>");
	      CallableStatement cstmt = dbcon.prepareCall(sqlCall);
//out.println("<H1>Doing SQL TRY4!</H1>");
	      cstmt.setString(1,title);
	      cstmt.setString(2,year);
	      cstmt.setString(3,director);
	      cstmt.setString(4,banner_url);
	      cstmt.setString(5,trailer_url);
              cstmt.setString(6,star_last_name);
	      cstmt.setString(7,genre_name);
//out.println("<H1>Doing SQL TRY6!</H1>");
	      cstmt.execute();
	      out.println("<H1>Doing SQL TRY2!</H1>");
              //cstmt.execute()// && request.getSession().getAttribute("EmployeeLoggedIn").equals("true")){
			out.println("<H1>Added!</H1>");
			nextPage = "/WEB-INF/sources/procedureOK.html";
		
		//else{
		//	nextPage = "/FabFlix/_dashboard.html";
		//out.println("<H1>NOT Added!</H1>");
		//}
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
              
		cstmt.close();
              dbcon.close();
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
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


