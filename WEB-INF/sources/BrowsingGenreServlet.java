
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;

public class BrowsingGenreServlet extends HttpServlet
{
        public String getServletInfo(){
                return "BrowsingServlet";
        }
        public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

	

        if(request.getParameter("homehome") == null &&request.getParameter("goBackHome") == null&&request.getParameter("nextprev") == null && request.getParameter("goBack") == null && request.getParameter("type") == null && request.getParameter("order") == null  && request.getParameter("ipp") == null){
                request.getSession().setAttribute("searchResult",new ArrayList<Movie>());
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displayMovieListGenre.jsp");
                rd.forward(request, response);

		PrintWriter out = response.getWriter();
		out.print("<h1>  Null parameter   </h1>");
                return;
        }
        ArrayList<Movie> searchResults = new ArrayList<>();
        String type = "genre";
	String order = "ASC";
	//String orderby = "title";
	String genre = null;
	        if(request.getParameter("homehome") != null){
                request.getSession().setAttribute("limitSession", null);
                request.getSession().setAttribute("pageSession", null);
                request.getSession().setAttribute("genre", null);
                 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");

                r.forward(request, response);
                return;
        }

	
	if(request.getParameter("goBack") != null){
		request.getSession().setAttribute("limitSession", null);
		request.getSession().setAttribute("pageSession", null);
		request.getSession().setAttribute("genre", null);
		 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/genre.jsp");

        	r.forward(request, response);
		return;
	}
        if(request.getParameter("goBackHome") != null){
                request.getSession().setAttribute("limitSession", null);
                request.getSession().setAttribute("pageSession", null);
                request.getSession().setAttribute("genre", null);
                 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");

                r.forward(request, response);
                return;
        }
        if (request.getParameter("type")!=null && request.getSession().getAttribute("genre")==null){
                request.getSession().setAttribute("limitSession", null);
                request.getSession().setAttribute("pageSession", null);
                request.getSession().setAttribute("genre", null);
                genre = (String) request.getParameter("type");
                request.getSession().setAttribute("genre", genre);
        }

        if (request.getParameter("type")!=null && request.getSession().getAttribute("genre")!=null){
                request.getSession().setAttribute("limitSession", null);
                request.getSession().setAttribute("pageSession", null);
                request.getSession().setAttribute("genre", null);
                genre = (String) request.getParameter("type");
                request.getSession().setAttribute("genre", genre);
        }


        if(request.getSession().getAttribute("orderBySession")==null){
                request.getSession().setAttribute("orderBySession", "title");
        }

        if(request.getSession().getAttribute("limitSession")==null){
                request.getSession().setAttribute("limitSession", "5");
        }

	if(request.getSession().getAttribute("pageSession")==null){
		request.getSession().setAttribute("pageSession", "1");
	}
	else{
		if(request.getParameter("nextprev")!=null){
		if(request.getParameter("nextprev").equals("next")){
			int k = Integer.parseInt((String)request.getSession().getAttribute("pageSession")) + 1;
			request.getSession().setAttribute("pageSession", String.valueOf(k));
			genre = (String) request.getSession().getAttribute("genre");
		}
		if (request.getParameter("nextprev").equals("prev")){
			int k = Integer.parseInt((String)request.getSession().getAttribute("pageSession")) - 1;
			request.getSession().setAttribute("pageSession", String.valueOf(k));
			genre = (String) request.getSession().getAttribute("genre");
		}
		}

	}



	if(request.getParameter("order") != null && !request.getParameter("order").isEmpty()){
		String or = request.getParameter("order");
		request.getSession().setAttribute("orderBySession", or);
		genre = (String) request.getSession().getAttribute("genre");
	}
	 if(request.getParameter("ipp")!=null&&!request.getParameter("ipp").isEmpty()){
		String	pageLimit =(String) request.getParameter("ipp");
		genre = (String) request.getSession().getAttribute("genre");
		request.getSession().setAttribute("limitSession", pageLimit);
	}
/*
	
	if (request.getParameter("type")!=null && request.getSession().getAttribute("genre")==null){
		genre = (String) request.getParameter("type");
		request.getSession().setAttribute("genre", genre);
	}
*/	
	int pageInt = Integer.parseInt((String)request.getSession().getAttribute("pageSession"));
	int pageLimitInt = Integer.parseInt((String)request.getSession().getAttribute("limitSession"));
	int limit, offset;
	String orderby = (String)request.getSession().getAttribute("orderBySession");
	if(pageInt == 1) {
		limit = pageLimitInt;
		offset = 0;
	} 
	else {
		limit = pageLimitInt;
		offset = (pageInt - 1) * limit;
	}
	genre = (String) request.getSession().getAttribute("genre");
        searchResults= Movie.getMovie(genre,type, order, orderby, limit, offset);
	PrintWriter out = response.getWriter();
//	out.println("<h1> message </h1>" + request.getParameter("order"));
	out.println("<h1> message </h1>" + request.getParameter("type"));
out.println("<h1> page  </h1>" + Integer.parseInt((String)request.getSession().getAttribute("pageSession")));
        out.println("<h1> limit </h1>" + Integer.parseInt((String)request.getSession().getAttribute("limitSession")));

out.println("<h1> genre </h1>" + (String)request.getSession().getAttribute("genre"));
out.println("*****************");
out.println("genre: "+ genre + "   type:   "+type + "  order: "+order + "  limit: " + limit + "  offset: "+offset);

        request.getSession().setAttribute("searchResult", searchResults);
 
//	out.println("<h1> message: size:  </h1>" + searchResults.size());
//	for(Movie m: searchResults)
//	out.println("id:  " + m.id +"   #### movie id:   " + m.year +  "     ^^  error:  " + m.title + "<br>");

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displayMovieListGenre.jsp");

        rd.forward(request, response);
        return;
    }
}
