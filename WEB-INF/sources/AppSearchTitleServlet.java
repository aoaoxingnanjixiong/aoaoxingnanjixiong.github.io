
/* A servlet to display the contents of the MySQL movieDB database */


import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class AppSearchTitleServlet extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http POST

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        String loginUser = "classta";
        String loginPasswd = "classta";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        PrintWriter out = response.getWriter();

	JSONArray jarray = new JSONArray();

        try
           {
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              Statement statement = dbcon.createStatement();
		String mtitle = "";
	if (request.getParameter("title")!=null && !request.getParameter("title").equals(""))
	     {
		 mtitle = request.getParameter("title");
	     }


	String query = "";
	 if(!mtitle.equals("")){
                String[] arrayOfString = mtitle.split("\\s+");
                for(int i = 0; i < arrayOfString.length; i++){
                        query = query + " +" + arrayOfString[i]+"*";
                }

        }




	      String query1 = "SELECT title, id from movies WHERE match(title) against('"+query+"'IN BOOLEAN MODE)";

              ResultSet rs = statement.executeQuery(query1);


              // Iterate through each row of rs
	if(rs.isBeforeFirst()){
              while (rs.next())
              {

		String t = rs.getString("title");
		int mid = rs.getInt("id");
		JSONObject jo = new JSONObject();
		jo.put("title", t);
		jo.put("movieId",mid);
		
		jarray.add(jo);
		
              }
	}else{
	      }

		JSONObject result = new JSONObject();
		result.put("Movieinfo",jarray);

		out.print(result);
		rs.close();
              statement.close();
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
                            "<P>SQL error in doPost: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
    }
}
