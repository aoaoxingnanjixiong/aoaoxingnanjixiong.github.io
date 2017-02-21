<!DOCTYPE html>
<html>
<head>
	<title>Search Page</title>

	<style type="text/css">
		form {
			text-align: center;
		}
		H4 {
			text-align: center;
		}
		input {
			text-align: center;
			height:30px;
			width:200px;			
		}

	</style>
<%--<script type="text/javascript">
	var isLoggedIn = "<%= (String)session.getAttribute("isLoggedIn")%>";
	if(isLoggedIn !== "true")
		windows.location.href="login.html"
</script>--%>
</head>

<body>
<H1 ALIGN="CENTER">Searching Page</H1>
<H4>Please input the movie title:</H4>
<form ACTION="/fabflix/searchtitle" METHOD="GET">
	
	<input type="text" name="mtitle">
	<input type="SUBMIT" value="Submit">	
</form>
</body>
</html>
