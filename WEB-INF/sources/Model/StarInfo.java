
package Model;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class StarInfo{
	public String name;
	public String date;
	public int id;
	public ArrayList<SimpleMovie> moviebystar;
	public String photo_url;
	public StarInfo(String name_, String date_, int id_, ArrayList<SimpleMovie> moviebystar_, String photo_url_){
		name = name_;
		date = date_;
		id = id_;
		moviebystar = moviebystar_;
		photo_url = photo_url_;
	}
	public StarInfo(){}
	public static StarInfo getStar(String arg){
//		String loginUser = "classta";
//        String loginPasswd = "classta";
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        try{
		Context initCtx = new InitialContext();

		Context envCtx = (Context) initCtx.lookup("java:comp/env");
               // Look up our data source

             	DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
             	Connection dbcon = ds.getConnection();

	    PreparedStatement statement = null;
		PreparedStatement statement2 = null;
	
            //String query = "SELECT * FROM stars WHERE stars.id = " + arg;
            String query = "SELECT * FROM stars WHERE stars.id = ? " ;
	    statement = dbcon.prepareStatement(query);
//		statement.setInt(1, Integer.parseInt(arg));	
		statement.setString(1, arg);
	    ResultSet rs = statement.executeQuery();

            ResultSet rss = null;
//StarInfo a =new StarInfo(null,null,1,null,null);
//return a;
            ArrayList<SimpleMovie> simplemovie = new ArrayList<SimpleMovie>();
	StarInfo to_return = new StarInfo();
	if(rs.next()){
	    int id1 = rs.getInt("id");
            String fname = rs.getString("first_name");
            String lname = rs.getString("last_name");
            String fullname = fname+" "+lname;
            String date = rs.getString("dob");
            String url = rs.getString("photo_url");
            //String squery = "SELECT movies.id, movies.title FROM movies WHERE movies.id in (SELECT movie_id FROM stars JOIN stars_in_movies ON stars.id = stars_in_movies.star_id WHERE stars.id=" + id1 + ")";
		String squery = "SELECT movies.id, movies.title FROM movies WHERE movies.id in (SELECT movie_id FROM stars JOIN stars_in_movies ON stars.id = stars_in_movies.star_id WHERE stars.id= ? )";
		statement2 = dbcon.prepareStatement(squery);
		statement2.setInt(1, id1);

            rss = statement2.executeQuery();
            while(rss.next()){
            	int mid = rss.getInt("id");
            	String title = rss.getString("title");
            	simplemovie.add(new SimpleMovie(mid,title));
            }
            to_return = new StarInfo(fullname, date, id1, simplemovie, url);
	}
            rs.close();
            rss.close();
            statement.close();
		statement2.close();
            dbcon.close();
            return to_return; 
        }catch(SQLException ex){
	    String a = "";
            while (ex != null) {
         //   System.out.println ("SQL Exception:  " + ex.getMessage ());
        a =  a + ex.getMessage ();  
	 ex = ex.getNextException ();
        }
	return new StarInfo(a,null,1,null,null);

         //	return null;
        }
        catch(java.lang.Exception ex){
    //    	return null;
      return new StarInfo(null,null,2,null,null);
	  }

	}
}

