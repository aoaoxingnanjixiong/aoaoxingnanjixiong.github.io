
//ParserMovie.fidToMid
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

public class ParserGenre extends DefaultHandler{

	private String tempVal;
	private int genreId;
	
	private Set<String> uniqueGenre;
	public static HashMap<String, Integer> genre_Info;
	String genreQuery = "Insert into genres (name) values";
	public static List<String> genreQueryTable;

	
	//to maintain context
	
	public ParserGenre(){
		
		genreId = -1;
		genre_Info = new HashMap<String, Integer>();
		uniqueGenre = new HashSet<String>();
		genreQueryTable = new ArrayList<String>();

	}
	
	public void runExample() {
		parseDocumentStarTable();
		printData();

	}

	private void parseDocumentStarTable() {
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
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
			Statement statementGenre = dbcon.createStatement();
			String maxId = "Select max(id) as maxGenreId from genres";
			ResultSet rs = statementGenre.executeQuery(maxId);
			if (rs.next()){
				genreId = rs.getInt("maxGenreId");
			}
			else
				genreId = 0;
							
			}catch (Exception ex) {
				System.out.println("Star Max Id Query Exception");
		}




		for(String s : uniqueGenre){
			//genreId++;
			genre_Info.put(s, ++genreId);
			System.out.println("genreId:    " + genreId);
		}
		//System.out.println(genre_Info.size());

		for(HashMap.Entry<String, Integer> entry: genre_Info.entrySet()){
			//System.out.println("Genre:  " + entry.getKey());
			//System.out.println("GenreId:  " + entry.getValue());
			//System.out.println(genreQuery + "(\"" + entry.getKey() + "\")");
			genreQueryTable.add(genreQuery + "(\"" + entry.getKey() + "\")");

		}
		
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		//System.out.println("****** startElement ********" + " uri: " + uri + "  localName: " + localName + "  qName: " + qName + "  type: " + attributes.getValue("type"));

		tempVal = "";

		
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
		//System.out.println("****** characters ********" + " ch: haha "  + "  start: " + start + "  length: " + length + " tempVal: " + tempVal);

	}
	

	public void endElement(String uri, String localName, String qName) throws SAXException {
		try{

			if (qName.equalsIgnoreCase("cat")) {
						//System.out.println("dirname");
				if(tempVal != null && !tempVal.equals("")){
					uniqueGenre.add(tempVal);
				}
			}


		}catch(Exception e){
			System.out.println("!!!!!!!!!!!!!!!!!! ParserGenre catch in endElement **************");
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

	}
	
*/
	
	
}




