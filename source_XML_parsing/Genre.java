
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;



public class Genre {

	private String genre;

	
	
	public Genre(){
		
	}
	
	public Genre(String genre_) {
		this.genre = genre_;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre_) {
		this.genre = genre_;
	}


	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Genre Details - ");
		sb.append("genre:" + getGenre());
		
		sb.append(".");
		
		return sb.toString();
	}
	
}
