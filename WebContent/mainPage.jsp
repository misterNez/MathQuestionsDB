<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>   

<%@ page import="com.me.webapps.mathquestions.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Math Problems</title>

	<link rel = "stylesheet" href = "styles.css" type = "text/css" />
		
</head>
<body>   

	<center><p class="bigFont" > <u>Math Question DB</u> </p></center>
	
	<form method="get" action="SearchKeywordServlet" class="right">
		<p class="smallFont"> 
			Enter a keyword:<input type="text" name="search" class="smallFont" />
			<input type="submit" value="Search" class="smallFont" />
  		</p>
  	</form>
  	
  	<br>
	
	<form method="post" action="AddQuestionServlet" class="indent">
		<p> 
			Enter a question:<input type="text" name="question"/>
  			<input type="submit" value="Add Question" /> 
  		</p>
  	</form>
  	
  	<table>
  	   <tr>
       	  <th colspan="2"> Questions </th>
       </tr>
      	  
       <c:forEach items="${sessionScope.questions}" var="item">
       <tr>
          <td class="left">
            	<c:out value="${item.question}"/>
          </td>
          
          <td>
          		<form method="get" action="AddKeywordServlet">
          			<input type="text" name="keyword"/>
          			<input type="hidden" name="quesID" value="${item.questionID}"/>
          			<input type="submit" value="Add Keyword"/>
          		</form>	
		  </td> 
       </tr>
     </c:forEach>
  	</table>
</body>
</html>