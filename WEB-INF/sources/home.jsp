<!DOCTYPE html>
<html>
<head>
	<title>Fabflix</title>
	<style type="text/css">
		div{
			text-align: center;
		}
		.buttons{
			height:50px;
			width:200px;
		}
		body{
                        margin-top: 100px;
		}
	</style>
<%--<script type="text/javascript">
	var isLoggedIn = "<%= (String)session.getAttribute("isLoggedIn")%>";
	if(isLoggedIn !== "true")
		windows.location.href="login.html"
</script>--%>
<script type="text/javascript">
</script>
</head>
<body>
<H1 ALIGN="CENTER">Fabflix</H1>

<div>
	<form ACTION="/fabflix/autocompletionsearch">
                <button TYPE = "submit" class="buttons" value="AutocompletionSearch" name="searchpage">Autocompletion_Search</button></a><br><br>

	<form ACTION="/fabflix/advancesearch">
                <button TYPE = "submit" class="buttons" value="AdvanceSearch" name="searchpage">AdvanceSearch</button></a><br><br>
        </form>

	<form ACTION="/fabflix/browsing">
                <button TYPE = "submit" class="buttons" value="Browsing by Genre" name="searchpage">Browsing by Genre </button></a><br><br>
        </form>

	<form ACTION="/fabflix/browsing">
                <button TYPE = "submit" class="buttons" value="Browsing by Title" name="searchpage">Browsing by Title</button></a><br><br>
	<form ACTION="/fabflix/readyCart">
                <button TYPE = "submit" class="buttons" value="Check Out" name="searchpage">Check Out</button></a><br><br>
        </form>
<br><br>
	<a href="<%= response.encodeURL(request.getContextPath() + "/readme.txt") %>">Read Me</a>

<br><br>
</div>



</body>

</html>





