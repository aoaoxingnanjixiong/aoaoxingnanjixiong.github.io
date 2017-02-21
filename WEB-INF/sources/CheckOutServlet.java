/*A servlet to check creditcard info */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class CheckOutServlet extends HttpServlet 
{
	public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http POST



    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
//        String loginUser = "classta";
//        String loginPasswd = "classta";
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
        out.println("<BODY><H1>Inside Login Servlet</H1>");

       if(request.getParameter("homehome") != null){
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");
              rd.forward(request, response);
        }
       if(request.getParameter("homehome2") != null){
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");
              rd.forward(request, response);
        }

        try
           {
		Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
               // Look up our data source

        DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
        Connection dbcon = ds.getConnection();

out.println("<H1>222222</H1>");
		PreparedStatement statement = null;
	      String cardnumber = request.getParameter("inputcardnumber");
	      String date = request.getParameter("expirationdate");
	      String firstname = request.getParameter("inputfirstname");
	      String lastname = request.getParameter("inputlastname");
out.println("<H1>" + cardnumber +  "</H1>");
	      String query = "SELECT * from creditcards WHERE id= ? AND expiration= ? AND first_name = ? AND last_name= ? ";
		statement = dbcon.prepareStatement(query);
		statement.setInt(1, Integer.parseInt(cardnumber));
out.println(cardnumber);
		statement.setString(2, date);
out.println(date);
		statement.setString(3, firstname);
out.println(firstname);
		statement.setString(4, lastname); 		
out.println(lastname);

              ResultSet rs = statement.executeQuery();

              out.println("<TABLE border>");
	      HttpSession session = request.getSession(true);
	      String nextPage="";

out.println("<H1>111111</H1>");
              // Iterate through each row of rs
              if (rs.next())
              {
		 session.setAttribute("isLoggedIn","true");
		 nextPage = "/WEB-INF/sources/FinishShopping.jsp";
              }else{
		 nextPage = "/WEB-INF/sources/checkout.html";
	      }
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
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
    }
}
