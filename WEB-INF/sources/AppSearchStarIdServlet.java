

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

public class AppSearchStarIdServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

	response.setContentType("text/html");
        PrintWriter out = response.getWriter();

	if(request.getParameter("sid")==null || request.getParameter("sid").equals("")){
		out.print("mid not exist");
		return;
	}

	try{



	JSONArray jarray = new JSONArray();
	StarInfo searchResults= StarInfo.getStar(request.getParameter("sid"));

    	//searchResults= Movie.getMovie(request.getParameter("mid"),type,"ASC", "title", 1, 0);

	String starq = "";
	if (searchResults!=null){
			
			JSONObject tempStar = new JSONObject();
			tempStar.put("starId", searchResults.id);
			tempStar.put("starName", searchResults.name);
			tempStar.put("starDate", searchResults.date);

			JSONArray arrayMovie = new JSONArray();		

			if(searchResults.moviebystar.size() >0){
				
				for (SimpleMovie s: searchResults.moviebystar){
					JSONObject tempMovie = new JSONObject();
					tempMovie.put("movieId",s.id);
					tempMovie.put("movieTitle", s.title);
					arrayMovie.add(tempMovie);
					//tempMovie.put("starMovie", arrayMovie);
				}
			tempStar.put("starMovie", arrayMovie);
			jarray.add(tempStar);
		
		}
		
	}else{out.print("search result null");}

	JSONObject result = new JSONObject();
	result.put("StarInfo", jarray);
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

