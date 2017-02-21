
//ParserMovie.fidToMid
//import java.io.IOException;
import java.util.*;
import java.net.*;
import java.io.*;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class ParserStar_in_movie extends DefaultHandler{

	private String tempVal;
	private int movieId;
	private int starId;
	

	//*******************  star_in_movie table element ********************
	String stars_in_moviesTableQuery = "Insert into stars_in_movies (star_id,movie_id) values";
	List<String> stars_in_moviesQuery;
	
	//to maintain context
	
	public ParserStar_in_movie(){
		
		stars_in_moviesQuery = new ArrayList<String>();
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
			
			sp.parse("casts124.xml", this);
			
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
		//for (String s : stars_in_moviesQuery)
		//	System.out.println(s);
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		//System.out.println("****** startElement ********" + " uri: " + uri + "  localName: " + localName + "  qName: " + qName + "  type: " + attributes.getValue("type"));

		tempVal = "";
		if(qName.equalsIgnoreCase("m")) {
			movieId = -1;
		}

		
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
		//System.out.println("****** characters ********" + " ch: haha "  + "  start: " + start + "  length: " + length + " tempVal: " + tempVal);

	}
	

	public void endElement(String uri, String localName, String qName) throws SAXException {
		try{


			if (qName.equalsIgnoreCase("f")){
				//System.out.println("f:  " + tempVal);
				if(ParserMovie.fidToMid.get(tempVal) != null)
					movieId = ParserMovie.fidToMid.get(tempVal);
			}
			else if (qName.equalsIgnoreCase("a")){
				//System.out.println("a:  " + tempVal);
				if(ParserStar.star_stagename_to_starInfo.get(tempVal) != null && movieId != -1){
					starId = ParserStar.star_stagename_to_starInfo.get(tempVal).getSnum();
				
					String tempQuery = "(" + starId + " , " + movieId + ")";
					
					stars_in_moviesQuery.add(stars_in_moviesTableQuery + tempQuery);
				}
			}

	

		}catch(Exception e){
			System.out.println("!!!!!!!!!!!!!!!!!! ParserStar_in_movie catch in endElement **************");
			return;
		}
	}
	
	
	
}




