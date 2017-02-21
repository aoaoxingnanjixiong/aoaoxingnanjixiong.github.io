
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import java.sql.*;
import java.net.*;


public class RunParsing{

	public static void main(String[] args){
	Connection dbcon = null;

	String loginUser = "classta";
		String loginPasswd = "classta";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		try{
			//Class.forName("org.gjt.mm.mysql.Driver");

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//System.out.println("#####");

			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			//System.out.println("#####");
		}catch (Exception ex) {
			System.out.println("Cannot connect to the database.");
			//System.exit(-1);
		}


	

	// genres
	try{
		dbcon.setAutoCommit(false);

	try{
		ParserGenre parserGenre = new ParserGenre();
		parserGenre.runExample();
		Statement statement1 = dbcon.createStatement();	
		List<String> genreQueryList = parserGenre.genreQueryTable;
		for(String s: genreQueryList){
			System.out.println(s);
			statement1.executeUpdate(s);
		}
	}catch (Exception ex) {
			System.out.println("Genre Query Exception");
			//System.exit(-1);
		}


	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

	//movies
	ParserMovie speMovie = new ParserMovie();
	speMovie.runExample();
	try{
		//dbcon.setAutoCommit(false);
		Statement statement2 = dbcon.createStatement();
		
		List<String> movieQueryList = speMovie.movieQuery;
		for(String s1: movieQueryList){
			System.out.println(s1);
			statement2.executeUpdate(s1);
		}
		//dbcon.commit();
	}catch (Exception ex) {
			System.out.println("movies Query Exception");
			//System.exit(-1);
		}


	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");


	//genres_in_movie
	List<String> genres_in_moviesQueryList = speMovie.genres_in_moviesQuery;
	try{
		Statement statement3 = dbcon.createStatement();
		for(String s2: genres_in_moviesQueryList){
			System.out.println(s2);
			statement3.executeUpdate(s2);
		}
	}catch (Exception ex) {
			System.out.println("movies Query Exception");
			//System.exit(-1);
		}


	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

	//stars
	ParserStar parserStar = new ParserStar();
	parserStar.runExample();
	try{
		Statement statement4 = dbcon.createStatement();
		List<String> starQueryList = parserStar.starQuery;
		for(String s3: starQueryList){
			System.out.println(s3);
			statement4.executeUpdate(s3);
		}
	}catch (Exception ex) {
			System.out.println("stars Query Exception");
			//System.exit(-1);
		}


	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");


	//stars in move
	ParserStar_in_movie speStar_in_movie = new ParserStar_in_movie();
	speStar_in_movie.runExample();

	try{
		Statement statement5 = dbcon.createStatement();
		List<String> stars_in_moviesQueryList = speStar_in_movie.stars_in_moviesQuery;
		for(String s4: stars_in_moviesQueryList){
			System.out.println(s4);
			statement5.executeUpdate(s4);
		}
	}catch (Exception ex) {
			System.out.println("stars Query Exception");
			//System.exit(-1);
		}
		dbcon.commit();
	}catch(Exception ex){
		System.out.println("main Exception");
	}


	
	}
}

