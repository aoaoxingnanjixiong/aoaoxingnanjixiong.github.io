

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;

public class SearchTitleServlet extends HttpServlet
{
	public String getServletInfo(){
		return "SearchTitle";
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

    	if(request.getParameter("goHome") == null && request.getParameter("mtitle") == null){
    		request.getSession().setAttribute("singleMovieResult",new ArrayList<Movie>());
    		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displaySingleMovie.jsp");
    		rd.forward(request, response);
    		return;
    	}
	if(request.getParameter("goHome") != null){
		RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");
                r.forward(request, response);
		request.getSession().setAttribute("singleMovieResult",null);
                return;

	}
    	ArrayList<Movie> searchResults = new ArrayList<>();
	String type = "title";
    	searchResults= Movie.getMovie(request.getParameter("mtitle"),type,"ASC", "title", 1, 0);

    	request.getSession().setAttribute("singleMovieResult", searchResults);
    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displaySingleMovie.jsp");
    	rd.forward(request, response);
    	return;
    }
}

