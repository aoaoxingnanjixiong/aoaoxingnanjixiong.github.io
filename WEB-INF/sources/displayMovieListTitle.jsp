<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.*" %>
<%@ page import="Model.*" %>

<!DOCTYPE html>
<html>
        <head>
                <title>movielist</title>
                <style type="text/css">
                        body{
                                text-align: center;
                        }
                        td {
                                text-align: left;
                        }
                        table{
                                margin: auto;
                                display:inline-block;
                        }
                        img{
                                display:inline;
                                float: left;
                                margin-left:300px;
                                margin-top:50px;
                        }

                </style>


                <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>


        </head>
        <body>



                <h1>Search Result: </h1>

                <form ACTION="/fabflix/browsingbygenre"><input type="SUBMIT" value="title" name = "order">
                <form ACTION="/fabflix/browsingbygenre"><input type="SUBMIT" value="year" name = "order">


                <form ACTION = "/fabflix/browsingbygenre" METHOD = "GET">

<input type="SUBMIT" name = "nextprev" id="prevbutton" value = "prev">
<input type="SUBMIT" name = "nextprev" id="nextbutton" value = "next" >
<input type="SUBMIT" name = "ipp" id="ippbutton5" value = "5" >
<input type="SUBMIT" name = "ipp" id="ippbutton10" value = "10">
<input type="SUBMIT" name = "ipp" id="ippbutton15" value = "15">
<input type="SUBMIT" name = "ipp" id="ippbutton20" value = "20">

<input type="SUBMIT" name = "ipp" id="ippbutton25" value = "25">


</form>

<form ACTION = "/fabflix/browsingbygenre" METHOD = "GET">
<input type="SUBMIT" name = "goBack" id="backButton" value = "Back to Previous Page">
</form>

<form ACTION = "/fabflix/browsingbygenre" METHOD = "GET">
<input type="SUBMIT" name = "goBackHome" id="backButton" value = "Back to Home Page">
</form>

                <%
                        List<Movie> mlist =  (List<Movie>)session.getAttribute("searchResult2");
                        if(mlist == null) {
                                out.println("<H1> Null MovieList</H1>");
                                mlist = new ArrayList<Movie>();
                        }
                        if(mlist.size() == 0)
                                out.println("<H1>NO RESULT</H1>");
                        else{

                        for(Movie m : mlist){
                                out.println("<table><tbody>");
                                out.println("<tr><td>ID:</td><td>"+ m.id + "</td></tr>");

                %>






                        <tr><td>Title:</td><td><a href="searchtitle?mtitle=<%=m.title%>" id = "click" onmouseover = "funover(event, '<%=m.id%>')" onmouseout = "funout() "><%=m.title%></a></td></tr>

                <%
                                out.println("<tr><td>Year:</td><td>" + m.year + "</td></tr>");
                                out.println("<tr><td>Stars:</td><td>");
                %>
                <%                      for(Star s : m.starInfo){
                                        out.println(s.name);%>

                                <form ACTION="/fabflix/showStars"><input type="SUBMIT" value = "<%=s.id%>"name = "who"> (click id to know more about the star)</form>
                <%
                                }


                                out.println("</td></tr>");
                                out.println("<tr><td>Director:</td><td>" + m.director + "</td></tr>");
                %>
                                <tr><td>Purchase:</td><td><form ACTION="/fabflix/shoppingCart"><input type="SUBMIT" value = "<%=m.id%>" name = "MovieId"> (click id to add to cart and check out)</form></td></tr>
                                <img src = "<%=m.banner_url%>" width="120" height="120"><br>
                <%
                                out.println("<br><br>");
                        }
                        }

                        session.setAttribute("searchResult2",null);
                %>





                <script language="JavaScript" type="text/javascript">
                
                        function funover(e, id) {               
                                console.log(id);
                                var ajReq;  // The variable that makes Ajax possible!

                                var posx = 0;
                                var posy = 0;
                                if (!e) var e = window.event;
                                if (e.pageX || e.pageY)
                                {
                                        console.log("event pagex");
                                        posx = e.pageX;
                                        posy = e.pageY;
                                }
                                else if (e.clientX || e.clientY)
                                {
                                        posx = e.clientX;
                                        posy = e.clientY;
                                }
                                var x = posx + "px";
                                var y = posy + "px";


                                try{
                                        // Opera 8.0+, Firefox, Safari

                                console.log("try");
                                ajReq = new XMLHttpRequest();
                                } catch (e){
                                        // Internet Explorer Browsers
                                        console.log("catch");
                                        try{
                                                ajReq = new ActiveXObject("Msxml2.XMLHTTP");
                                        } catch (e) {
                                                try{
                                                        ajReq = new ActiveXObject("Microsoft.XMLHTTP");
                                                } catch (e){
                                                        // Something went wrong
                                                        alert("Your browser broke!");
                                                        return false;
                                                }
                                        }
                                }
                                var innerText="";
                                console.log("before reday");
                                ajReq.onreadystatechange = function(){
                                console.log("before reday2");
                                if(ajReq.readyState == 4){

                                        console.log("in reday");
                                        var result =  ajReq.responseText;
                                        var result_array = [];
                                        result_array = result.split('!');
                                        for (var i = 0; i < result_array.length; i++){
                                                innerText = innerText + result_array[i]+"&#13;&#10;";
                                        }
                                         console.log(id);
                                document.getElementById('aaa').innerHTML = innerText; 
                                document.getElementById('popup').style.left = x;
                                document.getElementById('popup').style.top = y;
                        
                                console.log("xxxxxx");
                                 console.log("x = " + posx + "  y = " + posy);
                                document.getElementById("popup").style.display = 'block';



                                }



                        }
                        ajReq.open("GET", "/fabflix/searchbyid?mid="+id, true);
                        ajReq.send();
                        }

                        function funout() {
                                document.getElementById("popup").style.display = 'none';
                        }


                </script>
                <div id="popup" style="position:absolute; display:none;">
                        <textarea rows="30" cols="60" id="aaa">At w3schools.com you will learn how to make a website. We offer free tutorials in all web development technologies.
                        </textarea>
                </div>





        </body>

</html>




~                                                          
