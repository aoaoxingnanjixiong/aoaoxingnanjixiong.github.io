

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;

public class SearchIdServlet extends HttpServlet
{
	public String getServletInfo(){
		return "SearchTitle";
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

	if(((String)request.getSession(false).getAttribute("isLoggedIn")).equals("false") || request.getSession(false).getAttribute("isLoggedIn")==null){
                RequestDispatcher rd = request.getRequestDispatcher("login.html");
                rd.forward(request, response);
                return;
        }

	


	response.setContentType("text/html");
        PrintWriter out = response.getWriter();

	if(request.getParameter("mid")==null || request.getParameter("mid").equals("")){
		out.print("mid not exist");
		return;
	}

	try{
    	ArrayList<Movie> searchResults = new ArrayList<>();
	String type = "id";

    	searchResults= Movie.getMovie(request.getParameter("mid"),type,"ASC", "title", 1, 0);

	String result = "";
	String starq = "";
	if (searchResults!=null){
		for (Movie m : searchResults){
			result = "movie id: " + m.id + "!" + "movie title: " + m.title + "!" + "movie director: " + m.director + "!" + "movie year: " + m.year + "!" +"movie trailer: " + m.trailer_url;
			
			starq = "";
			if(m.starInfo.size() >0){
				for(Star s : m.starInfo){
					starq = starq + "!" + "Star: " + s.name;
				}
			}
		
		}
		result = result + starq;
	}else{out.print("search result null");}
	out.print(result);
	}catch(java.lang.Exception ex)
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

