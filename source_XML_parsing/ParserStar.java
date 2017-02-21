
//ParserMovie.fidToMid
//import java.io.IOException;
import java.util.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.net.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class ParserStar extends DefaultHandler{

	private String tempVal;
	private int starId;
	private String stageN;
	
	//*******************  star table element ********************
	List<Star> myStar;
	String starTableQuery = "Insert into stars (first_name,last_name, dob) values";
	public static List<String> starQuery;
	private Star tempStar;
	public static HashMap<String, Star> star_stagename_to_starInfo;

	
	//to maintain context
	
	public ParserStar(){
		myStar = new ArrayList<Star>();
		starQuery = new ArrayList<String>();
		starId = -1;
		star_stagename_to_starInfo = new HashMap<String, Star>();
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
			
			sp.parse("actors63.xml", this);
			
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
		
		//System.out.println("No of Star '" + myStar.size() + "'.");
	
		//Iterator it = myStar.iterator();
		//while(it.hasNext()) {
		//	System.out.println(it.next().toString());
		//}

		//for(String s : starQuery)
		//	System.out.println(s);

		for(Map.Entry<String, Star> entry: star_stagename_to_starInfo.entrySet()){

			//if(starId == -1)
			//	starId = 0; //should be max id from stars table
			//starId ++;

			//entry.getValue().setSnum(starId);

			System.out.println("Key: " + entry.getKey());
			System.out.println("firstNanme: " + entry.getValue().getFname());
			System.out.println("lastNanme: " + entry.getValue().getLname());
			System.out.println("STAR ID: " + entry.getValue().getSnum());
		}

		//System.out.println(starQuery.size());
		//System.out.println(star_stagename_to_starInfo.size());		
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		//System.out.println("****** startElement ********" + " uri: " + uri + "  localName: " + localName + "  qName: " + qName + "  type: " + attributes.getValue("type"));

		tempVal = "";

		if(qName.equalsIgnoreCase("stagename")) {
			tempStar = new Star();

		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
		//System.out.println("****** characters ********" + " ch: haha "  + "  start: " + start + "  length: " + length + " tempVal: " + tempVal);

	}
	

	public void endElement(String uri, String localName, String qName) throws SAXException {
		try{

			if (qName.equalsIgnoreCase("stagename")){
				//stars_in_moviesQuery.put(tempVal, tempStar); //create a map for this star
				stageN = tempVal;
			}
			else if (qName.equalsIgnoreCase("familyname")){

				tempStar.setLname(tempVal);
			}
			else if (qName.equalsIgnoreCase("dob")){
				System.out.println("date of birth:    " + tempVal);
				if(tempVal!=null && !tempVal.equals(""))
					tempStar.setDate(tempVal);
				else
					tempStar.setDate("");

				if (!(tempStar.getLname() == null || tempStar.getLname().equals("") || tempStar.getLname().equals(" ")) || !(tempStar.getFname() == null || tempStar.getFname().equals("") || tempStar.getFname().equals(" "))){
					
					if(starId == -1){
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
							Statement statementStar = dbcon.createStatement();
							String maxId = "Select max(id) as maxStarId from stars";
							ResultSet rs = statementStar.executeQuery(maxId);
							if (rs.next()){
								starId = rs.getInt("maxStarId");
							}
							else
								starId = 0;
							
						}catch (Exception ex) {
							System.out.println("Star Max Id Query Exception");
						}
					}
					starId ++;

					tempStar.setSnum(starId);
					System.out.println("starId:   " + starId);

					star_stagename_to_starInfo.put(stageN, tempStar);
					String tempQuery = "(\"" + tempStar.getFname() + "\",\"" + tempStar.getLname() + "\",\"" + tempStar.getDate() + "\")";

					starQuery.add(starTableQuery+tempQuery);
				}
			}
			else if (qName.equalsIgnoreCase("firstname")){
				
				if(tempVal.length()>0){
					if((tempVal.substring(tempVal.length() - 1)).equals("\\")){
						//System.out.println("$$$$$$$$$$$$");
						tempVal = tempVal.substring(0,tempVal.length()-1);
					}
				}

				tempStar.setFname(tempVal);

				if((tempStar.getLname() == null || tempStar.getLname().equals("")) && (tempStar.getFname() != null && !tempStar.getFname().equals(""))){
					tempStar.setLname(tempStar.getFname());
					tempStar.setFname("");
				}

				

				//myStar.add(tempStar);
				//inser to star_stagename_to_starInfo map with stage name and nonempty real names
				/*
				if (!(tempStar.getLname() == null || tempStar.getLname().equals("") || tempStar.getLname().equals(" ")) || !(tempStar.getFname() == null || tempStar.getFname().equals("") || tempStar.getFname().equals(" "))){
					
					if(starId == -1){
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
							Statement statementStar = dbcon.createStatement();
							String maxId = "Select max(id) as maxStarId from stars";
							ResultSet rs = statementStar.executeQuery(maxId);
							if (rs.next()){
								starId = rs.getInt("maxStarId");
							}
							else
								starId = 0;
							
						}catch (Exception ex) {
							System.out.println("Star Max Id Query Exception");
						}
					}
					starId ++;

					tempStar.setSnum(starId);
					System.out.println("starId:   " + starId);

					star_stagename_to_starInfo.put(stageN, tempStar);
					String tempQuery = "(\"" + tempStar.getFname() + "\",\"" + tempStar.getLname() + "\",\"" + tempStar.getDate() + "\")";

					starQuery.add(starTableQuery+tempQuery);
				}*/
			}





		}catch(Exception e){
			System.out.println("!!!!!!!!!!!!!!!!!! ParserStar catch in endElement **************");
			return;
		}
	}



		///    TEST DELET LATER

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
			System.out.println("Genre Query Exception");
			//System.exit(-1);
		}


	}
	
*/
	
}




