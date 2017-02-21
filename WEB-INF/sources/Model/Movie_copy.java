package Model;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class Movie{
	public int id;
	public String title;
	public int year;
	public ArrayList<Star> starInfo;
	public String director;
	public String banner_url;
	public String trailer_url;
	public Movie(int id_, String title_, int year_, ArrayList<Star>starInfo_, String director_, String banner_url_, String trailer_url_){
		id = id_;
		title = title_;
		year = year_;
		starInfo = starInfo_;
		director = director_;
		banner_url = banner_url_;
		trailer_url = trailer_url_;
	}

	public static ArrayList<Movie> adGetMovie(String q, String sort, String by,int mlimit, int moffset){
                String loginUser = "classta";
                String loginPasswd = "classta";
                String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		ArrayList<Movie> movieList = new ArrayList<>();
		try{
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

			Statement statement1 = dbcon.createStatement();
			Statement statement2 = dbcon.createStatement();
			String limit = String.valueOf(mlimit);
                	String offset = String.valueOf(moffset);

//			ArrayList<Movie> movieList = new ArrayList<>();
			String squery = null;
			String query  = q + " ORDER BY " + by + " "  + sort + " LIMIT " + limit + " OFFSET " + offset;
			ResultSet rs = statement1.executeQuery(query);
			ResultSet rss = null;
			if(rs.isBeforeFirst()){
                                while (rs.next()){
                                         ArrayList<Star> star = new ArrayList<Star>();
                                        int id = rs.getInt("id");
                                        String title = rs.getString("title");
                                        Integer year = rs.getInt("year");
                                        String director = rs.getString("director");
                                        String url = rs.getString("banner_url");
					String trailer = rs.getString("trailer_url");
                                        squery = "SELECT first_name, last_name, id FROM stars JOIN stars_in_movies ON stars.id = stars_in_movies.star_id WHERE stars_in_movies.movie_id = " + id;
                                        rss = statement2.executeQuery(squery);
                                        while (rss.next()){
                                                String fname = rss.getString("first_name");
                                                String lname = rss.getString("last_name");
                                                String fullname = fname + " " + lname;
                                                int starid = rss.getInt("id");
                                                star.add(new Star(starid, fullname));
                                        }
                                        movieList.add(new Movie(id, title, year, star, director, url, trailer));
                                }
			}
		        rs.close();
                        rss.close();
                        statement1.close();
                        statement2.close();
                        dbcon.close();
                        return movieList;
				
		}catch(SQLException ex){
                        while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
                   		ex = ex.getNextException ();
                }
            	return movieList;
                }
                catch(java.lang.Exception ex){
                        return null;
                }
	}


	public static ArrayList<Movie> getMovie(String arg, String type, String sort, String by,int mlimit, int moffset){
		String loginUser = "classta";
		String loginPasswd = "classta";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

		ArrayList<Movie> movieList = new ArrayList<>();
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement statement1 = dbcon.createStatement();
			Statement statement2 = dbcon.createStatement();

			String limit = String.valueOf(mlimit);
			String offset = String.valueOf(moffset);
			String query = null;
			String squery = null;
			if(type.equals("title")){
				query = "SELECT * FROM movies WHERE title =\"" +  arg  + "\"";
			}
			else if(type.equals("genre")){
				query = " SELECT * FROM movies WHERE movies.id in ( SELECT movie_id FROM genres join genres_in_movies ON genres.id = genres_in_movies.genre_id WHERE genres.name = '" + arg + "') ORDER BY " + by + " "  + sort + " LIMIT " + limit + " OFFSET " + offset;
			}
			else if(type.equals("firstLetter")){
				query = "SELECT * FROM movies WHERE title like '" +  arg  + "%' ORDER BY " + by + " "  + sort + " LIMIT " + limit + " OFFSET " + offset;
			}
			else if(type.equals("id")){
                                query = "SELECT * FROM movies WHERE id =" +  arg ;
                        }

	
			ResultSet rs = statement1.executeQuery(query);


			 ResultSet rss = null;

			if(rs.isBeforeFirst()){

			//	ArrayList<Star> star = new ArrayList<Star>();


				while (rs.next()){


					 ArrayList<Star> star = new ArrayList<Star>();
					int id = rs.getInt("id");
					String title = rs.getString("title");
					Integer year = rs.getInt("year");
					String director = rs.getString("director");
					String url = rs.getString("banner_url");
					String trailer = rs.getString("trailer_url"); 
					squery = "SELECT first_name, last_name, id FROM stars JOIN stars_in_movies ON stars.id = stars_in_movies.star_id WHERE stars_in_movies.movie_id = " + id;				


					rss = statement2.executeQuery(squery);


					while (rss.next()){
                                        	String fname = rss.getString("first_name");
                                        	String lname = rss.getString("last_name");
                                        	String fullname = fname + " " + lname;
						int starid = rss.getInt("id");
						 
	                                       	star.add(new Star(starid, fullname));
                               		}
					movieList.add(new Movie(id, title, year, star, director, url, trailer));
				}
			rss.close();
			}

			rs.close();
			statement1.close();
			statement2.close();
			dbcon.close();
			return movieList;

		}catch(SQLException ex){
String a = "";
			while (ex != null) {
a = a + ex.getMessage ();
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                   ex = ex.getNextException ();


                }
            return movieList; 
		}
		catch(java.lang.Exception ex){
// movieList.add(new Movie(2, "exception", 1, null, "2","2","@"));                                
//return movieList;
			return null;
		}
	}
}

