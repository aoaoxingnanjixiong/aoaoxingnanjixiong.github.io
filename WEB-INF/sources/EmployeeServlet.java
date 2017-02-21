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


import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;
public class EmployeeServlet extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http POST

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");    // Response mime type
        try{
		Context initCtx = new InitialContext();

                Context envCtx = (Context) initCtx.lookup("java:comp/env");

                DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
                Connection dbcon = ds.getConnection();              

		PreparedStatement statement = null;
              String useremail = request.getParameter("EmployeeEmail");
              String userpassword = request.getParameter("EmployeePassword");
              String query = "SELECT * from employees WHERE email= ? AND password = ?";
		statement = dbcon.prepareStatement(query);
		statement.setString(1, useremail);
		statement.setString(2, userpassword);

              ResultSet rs = statement.executeQuery();

              out.println("<TABLE border>");
//            HttpSession session = request.getSession(true);
              String nextPage="";

out.println("<H1>111111</H1>");
              // Iterate through each row of rs
              if (rs.next())
              {
                request.getSession().setAttribute("EmployeeLoggedIn","true");
                 nextPage = "/WEB-INF/sources/UI.html";
              }else{
                 request.getSession().setAttribute("EmployeeLoggedIn","false");
                 nextPage = "/FabFlix/_dashboard";
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
         


