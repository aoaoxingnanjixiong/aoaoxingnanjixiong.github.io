
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;



public class Movie {

	private String director;

	private String title;
	
	private int year;

	private String fid;

	
	public Movie(){
		
	}
	
	public Movie(String director_, String title_, int year_, String fid_) {
		this.director = director_;
		this.title = title_;
		this.year  = year_;		
		this.fid = fid_;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director_) {
		this.director = director_;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title_) {
		this.title = title_;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year_) {
		this.year = year_;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid_) {
		this.fid = fid_;
	}

	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Movie Details - ");
		sb.append("director:" + getDirector());
		sb.append(", ");
		sb.append("title:" + getTitle());
		sb.append(", ");
		sb.append("year:" + getYear());
		sb.append(", ");
		sb.append("fid:" + getFid());
		sb.append(", ");
		sb.append("mid:" + ParserMovie.fidToMid.get(getFid()));
		sb.append(".");
		
		return sb.toString();
	}
	
}
