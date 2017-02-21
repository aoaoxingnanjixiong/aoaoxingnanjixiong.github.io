
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
public class MetadataServlet extends HttpServlet
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
//
  //            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
		Context initCtx = new InitialContext();

                Context envCtx = (Context) initCtx.lookup("java:comp/env");
               // Look up our data source

                DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
                Connection dbcon = ds.getConnection();

		DatabaseMetaData dbmd = dbcon.getMetaData();
		ResultSet res = null;
		res = dbmd.getTables(null,null,"%",null);
		out.println("<html><title></title><body>");
		out.println("<form action=\"/fabflix/_dashboard\" method = \"get\">");
		out.println("<input type = \"submit\" name = \"UIProject3\" value = \"HomePage\"></form>");
		out.println("</body></html>");
		while(res.next()){
			String tableName = res.getString("TABLE_NAME");
			ResultSet columns = dbmd.getColumns(null,"moviedb",tableName,null);
			out.println("<HTML>");
			out.println("<head><style>table,td {border: 1px solid black;border-collapse: collapse;}td {padding: 15px;}</style></head>");
			out.println("<BODY>");
			out.println("<H1>" + tableName  + "</H1>");
			out.println("<table><tbody>");
			out.println("<tr><td>Attribute Name</td><td>Attribute Type</td></tr>");
			while(columns.next()){
				out.println("<H5><BODY><HEAD>"+"</td><td>" + columns.getString("COLUMN_NAME") + "</td><td>"+ columns.getString("TYPE_NAME") + "</td></tr>" + "</H5></BODY></HEAD>");
				//out.print("<HTML><BODY><H5>" + "  Attrobute Type: " + columns.getString("TYPE_NAME") + "\n" + "</H5></BODY></HEAD>");
			}
			out.println("</table></tbody>");
			out.println("<H5> ***************************************</H5>");
			out.println("</BODY></HTML>");
		}

		dbcon.close();
		//RequestDispatcher rd = request.getRequestDispatcher("dashboard.html");
                //rd.forward(request, response);
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


