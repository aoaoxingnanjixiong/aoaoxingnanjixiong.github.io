
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;



public class Star {

	private String fname;

	private String lname;

	private String date;	

	private String sid;

	private int snum;
	
	
	public Star(){
		
	}
	
	public Star(String fname_, String lname_, String date_, String sid_, int snum_) {
		this.fname = fname_;
		this.lname = lname_;
		this.date = date_;
		this.sid = sid_;
		this.snum = snum;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname_) {
		this.fname = fname_;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname_) {
		this.lname = lname_;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date_) {
		this.date = date_;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid_) {
		this.sid = sid_;
	}	

	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum_) {
		this.snum = snum_;
	}	


	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Star Details - ");
		sb.append("firstname:" + getFname());
		sb.append(", ");
		sb.append("lastname:" + getLname());
		sb.append(", ");
		sb.append("sid:" + getSid());
		sb.append(", ");
		sb.append("snum:" + getSnum());
		sb.append(".");

		
		return sb.toString();
	}
	
}
