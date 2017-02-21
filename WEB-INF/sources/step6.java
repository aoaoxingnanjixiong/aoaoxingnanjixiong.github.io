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

public class step6 extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        String loginUser = "classta";
        String loginPasswd = "classta";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
        out.println("<BODY><H1>MovieDB</H1>");
        response.setContentType("text/html");    // Response mime type
        try{
              out.println("<H1>In SQL TRY!</H1>");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
             out.println("<H1>Doing SQL TRY1!</H1>");
                 // Declare our statement
              String nextPage = "";
              String title = request.getParameter("inputtitle2");
out.println("<H1>Doing SQL TRY1!</H1>");
	      if (!request.getParameter("inputtitle2").equals("") && (!request.getParameter("inputgenre").equals("") || !request.getParameter("inputstarlastname").equals(""))){
	Statement statement = dbcon.createStatement();
	String query = "select * from movies where title = '" + title+"'";
	ResultSet rs = statement.executeQuery(query);
	if (rs.next()){
	      if (request.getParameter("inputgenre")!=""){
		String genre_name = request.getParameter("inputgenre");
		out.println("<H1>Doing genre  TRY1!</H1>");
		String sqlCall = "{call add_genre(?,?)}";
		CallableStatement cstmt = dbcon.prepareCall(sqlCall);
		cstmt.setString(1,genre_name);
		cstmt.setString(2,title);
		out.println("<H1>Doing exe TRY1!</H1>");
		cstmt.execute();
		nextPage = "/WEB-INF/sources/genreOK.html";
                cstmt.close();	
	      }
out.println("<H1>Doing SQL TRY3!</H1>");
	      if (request.getParameter("inputstarlastname")!=""){
		String star_name = request.getParameter("inputstarlastname");
		String sqlCall = "{call add_star(?,?)}";
		CallableStatement cstmt = dbcon.prepareCall(sqlCall);
		cstmt.setString(1,star_name);
                cstmt.setString(2,title);
                cstmt.execute();
		nextPage = "/WEB-INF/sources/starOK.html";
		cstmt.close();
	      }
	}else{
		nextPage = "/WEB-INF/sources/nullmovie.html";
	}
	statement.close();
	rs.close();
}else{
	nextPage = "WEB-INF/sources/UI.html";
}
out.println("<H1>Doing SQL TRY5!</H1>");
              //cstmt.execute()// && request.getSession().getAttribute("EmployeeLoggedIn").equals("true")){
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



