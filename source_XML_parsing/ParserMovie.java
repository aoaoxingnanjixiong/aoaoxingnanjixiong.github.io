
//import java.io.IOException;
import java.util.*;
import java.net.*;
import java.io.*;
import java.sql.*;



import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class ParserMovie extends DefaultHandler{

	private String tempVal;
	public  static HashMap<String, Integer> fidToMid;
	public int id;
	//*******************  movie table element ********************
	List<Movie> myMovie;
	String movieTableQuery = "Insert into movies (title,year,director) values";
	public static List<String> movieQuery;
	private Movie tempMovie;
	private String directorName;

	//*******************  genres_in_movies table element ********************
	//List<Genre> myGenre;
	int genreId;
	String genres_in_moviesTableQuery = "Insert into genres_in_movies (genre_id, movie_id) values";
	public static List<String> genres_in_moviesQuery;
	//private Genre tempGenre;

	
	
	//to maintain context
	
	
	
	public ParserMovie(){
		myMovie = new ArrayList<Movie>();
		movieQuery = new ArrayList<String>();
		genres_in_moviesQuery = new ArrayList<String>();
		fidToMid = new HashMap<String, Integer>();
		id = -1;
		genreId = -1;
	}
	
	public void runExample() {
		parseDocumentMovieTable();
		printData();
	}

	private void parseDocumentMovieTable() {
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse("mains243.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}




	/**
	 * Iterate through the list and print
	 * the contents
	 */
	private void printData(){
		
		//System.out.println("No of Movies '" + myMovie.size() + "'.");
	
		Iterator it = myMovie.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
		
		/*
		for(String s : movieQuery)
			System.out.println(s);
		for(String s2 : genres_in_moviesQuery)
			System.out.println(s2);
		for(HashMap.Entry<String, Integer> entry: fidToMid.entrySet()){
			System.out.println("fid:  " + entry.getKey());
			System.out.println("mid:  " + entry.getValue());
		}
		*/
		
		
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		//System.out.println("****** startElement ********" + " uri: " + uri + "  localName: " + localName + "  qName: " + qName + "  type: " + attributes.getValue("type"));


		tempVal = "";
		
		if(qName.equalsIgnoreCase("directorfilms"))
			directorName = "";

		if(qName.equalsIgnoreCase("film")) {
			genreId = -1;
			tempMovie = new Movie();
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
		//System.out.println("****** characters ********" + " ch: haha "  + "  start: " + start + "  length: " + length + " tempVal: " + tempVal);

	}
	

	public void endElement(String uri, String localName, String qName) throws SAXException {
		try{
		//System.out.println("****** endElement ********" + " uri: " + uri + "  localName: " + localName + "  qName: " + qName);

		if(qName.equalsIgnoreCase("dirname")) {
			directorName = tempVal;
			//System.out.println("dirname");
		}
		else if(qName.equalsIgnoreCase("film")){
						//System.out.println("film");

			tempMovie.setDirector(directorName);
			myMovie.add(tempMovie);
			String tempQuery = "(\"" + tempMovie.getTitle() + "\"," + String.valueOf(tempMovie.getYear()) + ",\"" +  directorName  + "\")";
			movieQuery.add(movieTableQuery+tempQuery);

			if(id == -1){
				Connection dbcon = null;

				String loginUser = "root";
				String loginPasswd = "3368";
				String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
				try{

					Class.forName("com.mysql.jdbc.Driver").newInstance();

					dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
				}catch (Exception ex) {
					System.out.println("Cannot connect to the database.");
				}
		
				try{
					Statement statementMovie = dbcon.createStatement();
					String maxId = "Select max(id) as maxMovieId from movies";
					ResultSet rs = statementMovie.executeQuery(maxId);
					if (rs.next()){
						id = rs.getInt("maxMovieId");
					}
					else
						id = 0;
							
				}catch (Exception ex) {
					System.out.println("Star Max Id Query Exception");
				}
			}
					id ++;
					System.out.println("movie id:   " + id);


			//if(id == -1)
			//	id = 0;//get the max id from the movie table
			//id ++;
			fidToMid.put(tempMovie.getFid(), id);

			if(genreId != -1){ // genre for this movie exist
				String tempQuery2 = "(" + genreId + " , " + id + ")";
				genres_in_moviesQuery.add(genres_in_moviesTableQuery + tempQuery2);
				//System.out.println(genres_in_moviesTableQuery + tempQuery2);
			}

		}
		else if (qName.equalsIgnoreCase("t")) {
						//System.out.println("t");

			tempMovie.setTitle(tempVal);
		}
		else if (qName.equalsIgnoreCase("year")) {
						//System.out.println("year");

			tempMovie.setYear(Integer.parseInt(tempVal));

		}
		else if (qName.equalsIgnoreCase("cat")) {
						//System.out.println("dirname");
			if(ParserGenre.genre_Info.get(tempVal) != null){

				genreId = ParserGenre.genre_Info.get(tempVal);
				//System.out.println("Genre:  " + tempVal + "   genreId : " + genreId);
			}
		}
		else if (qName.equalsIgnoreCase("fid")) {
			tempMovie.setFid(tempVal);
		}
		}catch(Exception e){
			System.out.println("!!!!!!!!!!!!!!!!!! catch in endElement **************");
			return;
		}
	}
/*
	public static void main(String[] args){
		Connection dbcon = null;

		String loginUser = "root";
		String loginPasswd = "3368";
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

	ParserMovie speMovie = new ParserMovie();
	speMovie.runExample();
	try{
		Statement statement2 = dbcon.createStatement();
		
		List<String> movieQueryList = speMovie.movieQuery;
		for(String s1: movieQueryList){
			System.out.println(s1);
			statement2.executeUpdate(s1);
		}
	}catch (Exception ex) {
			System.out.println("movies Query Exception");
			//System.exit(-1);
		}


	}
	*/
	
}




