
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


public class AutocompletionServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
	
	String a = "";
	if (req.getParameter("username")!=null){
		a = req.getParameter("username");
	}
    	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String query = "";


	if(!a.equals("")){
		String[] arrayOfString = a.split("\\s+");
		for(int i = 0; i < arrayOfString.length; i++){
               		query = query + " +" + arrayOfString[i]+"*";
        	}

	}

	String loginUser = "classta";
        String loginPasswd = "classta";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	ResultSet rs = null;


	try{
		Class.forName("com.mysql.jdbc.Driver").newInstance();

              	Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              	// Declare our statement
              	Statement statement = dbcon.createStatement();

		if(!query.equals("% ") && !query.equals("")){

			String query1 = "SELECT title from movies WHERE match(title) against ('"+query+"' IN BOOLEAN MODE)";
			rs = statement.executeQuery(query1);
		}


//		String query = "SELECT title from movies WHERE title LIKE '"+useremail+"' AND password='"+userpassword+"'";


		String result = "";
		if(rs!=null){

		while (rs.next()){
                        String title = rs.getString("title");
			result = result + title + ",";
		}
		}

		
		out.print(result);
		statement.close();
              	dbcon.close();
		out.close();
		rs.close();
	}catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception!!!:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet!!: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
  }
}


