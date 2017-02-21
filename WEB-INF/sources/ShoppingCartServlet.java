import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.*;

public class ShoppingCartServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
        
	{
	//	@SuppressWarnings("rawtypes")
//		@SuppressWarnings("unchecked")

        if(request.getParameter("homehome") != null){
                 RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/home.jsp");

                r.forward(request, response);
                return;
        }



	if(request.getParameter("checkout") != null){
		if(request.getParameter("checkout").equals("CheckOut")){
			   RequestDispatcher r = request.getRequestDispatcher("/WEB-INF/sources/checkout.html");
                           r.forward(request, response);
			   return;
		}		
	}

                ArrayList<MovieCart> am = new ArrayList<MovieCart>();
		int mid = 0;
		if(request.getParameter("MovieId") != null){
                String temp = request.getParameter("MovieId");
                 mid = Integer.parseInt(temp);
		}
                if(request.getSession().getAttribute("cartSession") == null){
                        request.getSession().setAttribute("cartSession", new ArrayList<MovieCart>()); 
                }
                else{
                       am=(ArrayList<MovieCart>) request.getSession().getAttribute("cartSession");
		 }
                boolean check = false;
		if(request.getParameter("MovieId") != null){
                for(MovieCart m: am){
                        if(m.id == mid){
                                check=true;
                                m.count++;
                        }
                }}
		if(request.getParameter("inputNumber")!=null && request.getParameter("movieId")!=null){
			int mmid = Integer.parseInt(request.getParameter("movieId"));
	                for(MovieCart m1: am){
                        if(m1.id == mmid){
                                check=true;
                                m1.count = Integer.parseInt(request.getParameter("inputNumber"));
                        }
                }

		}

                if(check == false){
                        MovieCart movie = MovieCart.getMovie(mid);
			if(movie.count != -1)
                       		am.add(movie);
                }

                request.getSession().setAttribute("cartSession", am);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/sources/shoppingCart.jsp");

        rd.forward(request, response);
        return;


        }
}

