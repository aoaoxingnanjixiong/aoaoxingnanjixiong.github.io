import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;

public class BrowsingTitleServlet extends HttpServlet
{
        public String getServletInfo(){
                return "BrowsingServlet";
        }
        public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {



        if(request.getParameter("homehome") == null&&request.getParameter("goBackHome") == null&&request.getParameter("nextprev") == null && request.getParameter("goBack") == null && request.getParameter("type") == null && request.getParameter("order") == null  && request.getParameter("ipp") == null){
                request.getSession().setAttribute("searchResult2",new ArrayList<Movie>());
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displayMovieListTitle.jsp");
                rd.forward(request, response);

		PrintWriter out = response.getWriter();
		out.print("<h1>  Null parameter   </h1>");
                return;
        }
        ArrayList<Movie> searchResults = new ArrayList<>();
        String type = "firstLetter";
	String order = "ASC";
	String title = null;
	
	if(request.getParameter("goBack") != null){
		request.getSession().setAttribute("limitSession2", null);
		request.getSession().setAttribute("pageSession2", null);
		request.getSession().setAttribute("title2", null);
		 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/title.jsp");

        	r.forward(request, response);
		return;
	}
        if(request.getParameter("goBackHome") != null){
                request.getSession().setAttribute("limitSession2", null);
                request.getSession().setAttribute("pageSession2", null);
                request.getSession().setAttribute("title2", null);
                 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");

                r.forward(request, response);
                return;
        }
        if(request.getParameter("homehome") != null){
                request.getSession().setAttribute("limitSession2", null);
                request.getSession().setAttribute("pageSession2", null);
                request.getSession().setAttribute("title2", null);
                 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");

                r.forward(request, response);
                return;
        }


        if (request.getParameter("type")!=null && request.getSession().getAttribute("title2")==null){
                request.getSession().setAttribute("limitSession2", null);
                request.getSession().setAttribute("pageSession2", null);
                request.getSession().setAttribute("title2", null);
                title = (String) request.getParameter("type");
                request.getSession().setAttribute("title2", title);
        }
	if (request.getParameter("type")!=null && request.getSession().getAttribute("title2")!=null){
                request.getSession().setAttribute("limitSession2", null);
                request.getSession().setAttribute("pageSession2", null);
                request.getSession().setAttribute("title2", null);
                title = (String) request.getParameter("type");
                request.getSession().setAttribute("title2", title);

	}

        if(request.getSession().getAttribute("orderBySession2")==null){
                request.getSession().setAttribute("orderBySession2", "title");
        }

        if(request.getSession().getAttribute("limitSession2")==null){
                request.getSession().setAttribute("limitSession2", "5");
        }

	if(request.getSession().getAttribute("pageSession2")==null){
		request.getSession().setAttribute("pageSession2", "1");
	}
	else{
		if(request.getParameter("nextprev")!=null){
		if(request.getParameter("nextprev").equals("next")){
			int k = Integer.parseInt((String)request.getSession().getAttribute("pageSession2")) + 1;
			request.getSession().setAttribute("pageSession2", String.valueOf(k));
			title = (String) request.getSession().getAttribute("title2");
		}
		if (request.getParameter("nextprev").equals("prev")){
			int k = Integer.parseInt((String)request.getSession().getAttribute("pageSession2")) - 1;
			request.getSession().setAttribute("pageSession2", String.valueOf(k));
			title = (String) request.getSession().getAttribute("title2");
		}
		}

	}

	if(request.getParameter("order") != null && !request.getParameter("order").isEmpty()){
		String or = request.getParameter("order");
		request.getSession().setAttribute("orderBySession2", or);
		title = (String) request.getSession().getAttribute("title2");
	}
	 if(request.getParameter("ipp")!=null&&!request.getParameter("ipp").isEmpty()){
		String	pageLimit =(String) request.getParameter("ipp");
		title = (String) request.getSession().getAttribute("title2");
		request.getSession().setAttribute("limitSession2", pageLimit);
	}

	
	int pageInt = Integer.parseInt((String)request.getSession().getAttribute("pageSession2"));
	int pageLimitInt = Integer.parseInt((String)request.getSession().getAttribute("limitSession2"));
	int limit, offset;
	String orderby = (String)request.getSession().getAttribute("orderBySession2");
	if(pageInt == 1) {
		limit = pageLimitInt;
		offset = 0;
	} 
	else {
		limit = pageLimitInt;
		offset = (pageInt - 1) * limit;
	}
	title = (String) request.getSession().getAttribute("title2");
        searchResults= Movie.getMovie(title,type, order, orderby, limit, offset);
	PrintWriter out = response.getWriter();
	out.println("<h1> message </h1>" + request.getParameter("type"));
out.println("<h1> page  </h1>" + Integer.parseInt((String)request.getSession().getAttribute("pageSession2")));
        out.println("<h1> limit </h1>" + Integer.parseInt((String)request.getSession().getAttribute("limitSession2")));

out.println("<h1> title </h1>" + (String)request.getSession().getAttribute("title2"));
out.println("*****************");
out.println("title: "+ title + "   type:   "+type + "  order: "+order + "  limit: " + limit + "  offset: "+offset);

        request.getSession().setAttribute("searchResult2", searchResults);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/displayMovieListTitle.jsp");

        rd.forward(request, response);
        return;
    }
}

