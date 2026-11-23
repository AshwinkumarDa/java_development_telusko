<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP Web Application</title>
</head>
<body>

<h1>JSP Web App to generate Dynamic Response</h1>
<h2>The changes we made in JSP will get auto deployed. Just refresh browser.</h2>

<!--  Directive Tag -->
<%@ page import="java.util.Date"
 
%>

<%!
// in declaration tag
int age = 19;
%>

<%
// Scriptlet tag

	String name = request.getParameter("uname");
	String ucity = request.getParameter("ucity");
	
	Date date = new Date();
	
	// out is implicit variable object.
	out.println("Hello "+ name);
	out.println("I know you're from " + ucity);
	out.println("Date: " + date);
%>

<!--  Expression Tag -->
<h1><%= date  %></h1>
<h2>Hello I know you are from <%= ucity  %></h2>
<h2>Hello I know your age is <%= age %> years.</h2>


</body>
</html>