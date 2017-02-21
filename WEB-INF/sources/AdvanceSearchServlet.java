
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;

public class AdvanceSearchServlet extends HttpServlet
{
        public String getServletInfo(){
                return "AdvanceSearchServlet";
        }
        public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

	PrintWriter out = response.getWriter();

        /*if(request.getParameter("title") == null && request.getParameter("year") == null && request.getParameter("director") == null && request.getParameter("first_name") == null && request.getParameter("last_name") == null){
		out.println("In Null");
			
		StarInfo searchResults = new StarInfo();
                request.getSession().setAttribute("advanceSearch", advanceSearchResults);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displayMovieAdvance.jsp");
                rd.forward(request, response);
		out.print("<h1>  Null parameter   </h1>");
                return;
        }*/

//********************************************************


//************************************************************************
boolean check = true;
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        if(request.getParameter("title")!=null && !request.getParameter("title").equals("")){
		String mt = "%" + request.getParameter("title") + "%";
                hashtable.put("title",mt);
                check = false;
        }
        if(request.getParameter("year")!=null && !request.getParameter("year").equals("")){
                hashtable.put("year",request.getParameter("year"));
                check = false;
        }
        if(request.getParameter("director")!=null && !request.getParameter("director").equals("")){
                hashtable.put("director",request.getParameter("director"));
                check = false;
        }
        if(request.getParameter("first_name")!=null && !request.getParameter("first_name").equals("")){
		out.println("$$:  First Name" );
                hashtable.put("first_name",request.getParameter("first_name"));
                check = false;
        }
        if(request.getParameter("last_name")!=null && !request.getParameter("last_name").equals("")){
                hashtable.put("last_name",request.getParameter("last_name"));
                check = false;
        }

        boolean hasStar = false;
        boolean hasMovie = false;
        String qStar = null;
        String qStarPrev = "SELECT * FROM movies WHERE (movies.id in (SELECT movie_id FROM stars JOIN stars_in_movies ON ";
        if(hashtable.containsKey("first_name") && hashtable.containsKey("last_name")){
                hasStar = true;
                String fname = hashtable.get("first_name");
                String lname = hashtable.get("last_name");
                qStar = "stars.id = stars_in_movies.star_id WHERE stars.first_name = '" + fname +"' AND stars.last_name = '" + lname + "'";
//                hashtable.remove("first_name");
//                hashtable.remove("last_name");
        }
        if(!hashtable.containsKey("first_name") && hashtable.containsKey("last_name")){
                hasStar = true;
                String lname = hashtable.get("last_name");
                qStar = "stars.id = stars_in_movies.star_id WHERE stars.last_name = '" + lname + "'";
//                hashtable.remove("last_name");
        }
        if(hashtable.containsKey("first_name") && !hashtable.containsKey("last_name")){
                hasStar = true;
                String lname = hashtable.get("first_name");
                qStar = "stars.id = stars_in_movies.star_id WHERE stars.last_name = '" + lname + "'";
 //               hashtable.remove("first_name");
        }


        String qMoviePrev = "SELECT * FROM movies WHERE ";
        String qMovie = "";
        String str = "";
        int n = 1;
        Set<String> keys = hashtable.keySet();
        Iterator<String> itr = keys.iterator();
        while(itr.hasNext()){
		str = itr.next();
		if(!str.equals("first_name") && !str.equals("last_name"))
                	hasMovie = true;
		if(str.equals("first_name") || str.equals( "last_name"))
			continue;
                if(n==1){
			if (!str.equals("title"))
                        qMovie = str + " = '" + hashtable.get(str)+"'";
			else
			qMovie = str + " LIKE '" + hashtable.get(str)+"'";
		}
		else{
		if(str.equals("title"))
			qMovie = qMovie + " AND " + str + " LIKE '" + hashtable.get(str)+"'";
		
                else{
                        qMovie = qMovie + " AND " + str + " = '" + hashtable.get(str)+"'";
                }
		}
                n++;
        }
        String queryPass = null;
        if(hasMovie == true && hasStar == false){
out.println("NO star +HAS  movie");
                queryPass = qMoviePrev + qMovie;
        }
        if(hasMovie == false && hasStar == true){
out.println("HAS star + NO  movie");
                queryPass = qStarPrev + qStar + "))";
        }
        if(hasMovie == true && hasStar == true){
		out.println("has star + movie");
                queryPass = qStarPrev + qStar + ")) AND " + qMovie;
        }


        if(check == false){
                request.getSession().setAttribute("limitSession3", null);
                request.getSession().setAttribute("pageSession3", null);
                request.getSession().setAttribute("orderBySession3", null);
                request.getSession().setAttribute("query", queryPass);
        }

//*********************************************************
        if(request.getParameter("homehome") != null){
                request.getSession().setAttribute("limitSession3", null);
                request.getSession().setAttribute("pageSession3", null);
                request.getSession().setAttribute("orderBySession3", null);
		RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");
                r.forward(request, response);
 
		return;
        }


	if(request.getParameter("goBack") != null){
		request.getSession().setAttribute("limitSession3", null);
		request.getSession().setAttribute("pageSession3", null);
		request.getSession().setAttribute("orderBySession3", null);
		 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/advancesearch.jsp");

        	r.forward(request, response);
		return;
	}
        if(request.getParameter("goBackHome") != null){
                request.getSession().setAttribute("limitSession3", null);
                request.getSession().setAttribute("pageSession3", null);
		request.getSession().setAttribute("orderBySession3", null);
                 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");

                r.forward(request, response);
                return;
        }


        if(request.getSession().getAttribute("orderBySession3")==null){
                request.getSession().setAttribute("orderBySession3", "title");
        }//ok

        if(request.getSession().getAttribute("limitSession3")==null){
                request.getSession().setAttribute("limitSession3", "5");
        }

	    if(request.getSession().getAttribute("pageSession3")==null){
		        request.getSession().setAttribute("pageSession3", "1");
	    }

	    
		if(request.getParameter("nextprev")!=null){
			if(request.getParameter("nextprev").equals("next")){
				int k = Integer.parseInt((String)request.getSession().getAttribute("pageSession3")) + 1;
				request.getSession().setAttribute("pageSession3", String.valueOf(k));
			}
		if (request.getParameter("nextprev").equals("prev")){
				int k = Integer.parseInt((String)request.getSession().getAttribute("pageSession3")) - 1;
				request.getSession().setAttribute("pageSession3", String.valueOf(k));
			}
		}
		

	    if(request.getParameter("order") != null && !request.getParameter("order").isEmpty()){
			String or = request.getParameter("order");
			request.getSession().setAttribute("orderBySession3", or);
		}

	 	if(request.getParameter("ipp")!=null&&!request.getParameter("ipp").isEmpty()){
			String	pageLimit =(String) request.getParameter("ipp");
			request.getSession().setAttribute("limitSession3", pageLimit);
		}

	
	int pageInt = Integer.parseInt((String)request.getSession().getAttribute("pageSession3"));
	int pageLimitInt = Integer.parseInt((String)request.getSession().getAttribute("limitSession3"));
	int limit, offset;
	String orderby = (String)request.getSession().getAttribute("orderBySession3");
	if(pageInt == 1) {
		limit = pageLimitInt;
		offset = 0;
	} 
	else {
		limit = pageLimitInt;
		offset = (pageInt - 1) * limit;
	}

//**************************
out.println("<h1> message: query:  </h1>" + queryPass);

//out.println("<h1> message: size:  </h1>" + searchResults.size());


hashtable.clear();
//***************************
	String readyQuery = (String)request.getSession().getAttribute("query");
	ArrayList<Movie> searchResults = new ArrayList<>();
	searchResults = Movie.adGetMovie(readyQuery, "ASC", orderby, limit, offset);
	request.getSession().setAttribute("searchResult3", searchResults);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displayMovieAdvance.jsp");
        rd.forward(request, response);
        return;
    }
}
