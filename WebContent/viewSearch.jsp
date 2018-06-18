<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>

	<link rel = "stylesheet" href = "styles.css" type = "text/css" />

</head>
<body>

	<% request.getAttribute("quesList"); %>
	
	<p class="center italic bigFont">
		<u>Search Results</u>
	</p>
	
	<p class="center">
		<c:forEach items="${quesList}" var="question">
    		<c:out value="${question}"/>
    		<br>
    	</c:forEach>
    </p>
    
    <br>
    
    <form method="get" action="ListQuestionServlet" class="center">
    	<input type="submit" value="Return to Main Page"/>
    </form>
 

</body>
</html>