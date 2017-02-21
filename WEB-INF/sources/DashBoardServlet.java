
/* A servlet to display the contents of the MySQL movieDB database */


import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class DashBoardServlet extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http POST

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();

//	String loginUser = "classta";
//        String loginPasswd = "classta";
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        try
        {
              //Class.forName("org.gjt.mm.mysql.Driver");
  //            Class.forName("com.mysql.jdbc.Driver").newInstance();

    //          Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

		 Context initCtx = new InitialContext();

                Context envCtx = (Context) initCtx.lookup("java:comp/env");
               // Look up our data source

                DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
                Connection dbcon = ds.getConnection();
              // Declare our statement
              Statement statement = dbcon.createStatement();
              String firstName = request.getParameter("inputfirstname");
              String lastName = request.getParameter("inputlastname");
	      String date = request.getParameter("inputdob");
	      String url = request.getParameter("photo_url");
	     
		String q = "insert into stars (first_name, last_name, dob, photo_url) values(?, ?, ?, ?)"; 
		try{
		PreparedStatement ps = dbcon.prepareStatement(q);
		if(firstName.equals("") && lastName.equals("")){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/UI.html");
                	rd.forward(request, response);
		}
		if(!firstName.equals("") && !lastName.equals("")){
			ps.setString(1,firstName);
			ps.setString(2,lastName);
		}
		else if(!lastName.equals("")){
			ps.setString(1,"");
			ps.setString(2,lastName);
		}
		else if(!firstName.equals("")){
			ps.setString(1,"");
			ps.setString(2,firstName);
		}
		ps.setString(3,date);
		ps.setString(4,url);
		ps.execute();
		dbcon.close();
		ps.close();		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/addstarsuccess.html");
                rd.forward(request, response);
		}catch(Exception e){
			 out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            e.getMessage() + "</P></BODY></HTML>");
                return;

		}
	}catch(Exception e){
		out.println("<HTML>" +
     		            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            e.getMessage() + "</P></BODY></HTML>");
                return;
	
	}

        // Output stream to STDOUT

       out.println("<HTML><HEAD><TITLE>Dash_board</TITLE></HEAD>");


	    //  RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/dashboard.html");
	    //  rd.forward(request, response);	
      

    }
}
