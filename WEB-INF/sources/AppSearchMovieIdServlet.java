

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class AppSearchMovieIdServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

	response.setContentType("text/html");
        PrintWriter out = response.getWriter();

	if(request.getParameter("mid")==null || request.getParameter("mid").equals("")){
		out.print("mid not exist");
		return;
	}

	try{
    	ArrayList<Movie> searchResults = new ArrayList<>();
	String type = "id";
	JSONArray jarray = new JSONArray();
    	searchResults= Movie.getMovie(request.getParameter("mid"),type,"ASC", "title", 1, 0);

	String starq = "";
	if (searchResults!=null){
		for (Movie m : searchResults){
			
			JSONObject tempMovie = new JSONObject();
			tempMovie.put("movieId", m.id);
			tempMovie.put("movieTitle", m.title);
			tempMovie.put("movieDirector", m.director);
			tempMovie.put("movieYear",m.year);
			tempMovie.put("movieTrailer", m.trailer_url);

			JSONArray arrayStar = new JSONArray();		

			if(m.starInfo.size() >0){
				
				for(Star s : m.starInfo){
					JSONObject tempStar = new JSONObject();
					tempStar.put("starId",s.id);
					tempStar.put("starName", s.name);
					arrayStar.add(tempStar);
					tempMovie.put("movieStars", arrayStar);
				}
			}
			jarray.add(tempMovie);
		
		}
		
	}else{out.print("search result null");}

	JSONObject result = new JSONObject();
	result.put("MovieInfo", jarray);
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

