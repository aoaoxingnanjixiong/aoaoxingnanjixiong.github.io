package Model;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
public class MovieCart{
        public String title;
        public int id;
        public int count;
        public MovieCart(String title_, int id_, int count_){
                title = title_;
                id = id_;
                count = count_;
        }
        public  MovieCart(){
               title = null;
                id = 0;
                count = -1;
        }
        public static MovieCart getMovie(int id){
        String loginUser = "classta";
        String loginPasswd = "classta";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        MovieCart to_return = new MovieCart();
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            Statement statement = dbcon.createStatement();
            String query = "SELECT * FROM movies WHERE movies.id = " + id;
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                int mid = rs.getInt("id");
                String title = rs.getString("title");
                to_return = new MovieCart(title, mid, 1);
            }
            rs.close();
            statement.close();
            dbcon.close();
            return to_return;

        }catch(SQLException ex){
	        return to_return;
        }
        catch(java.lang.Exception ex){
                return null;
        }

        }

}

