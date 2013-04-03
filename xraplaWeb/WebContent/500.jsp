<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<%@ page import="java.io.PrintWriter" %>
<head>
	<title>CoolRapla</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css"/>
	
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script>
		function popup() {
			var w = window.open();
			$(w.document.body).html($("#error_details").html());
		}
	</script>
</head>
<body>
	<div id="error_container">
		<h1>500</h1>
		<h2>We balls'd it up.</h2>
		<span class="line">
			An internal server error occured while processing your request.
		</span><br>
		<span class="line">
			This is entirely our fault. Consider donating some hot beverages to help us fix the error.
		</span><br>
		<span class="line">
			Click <a href="javascript:void(0);" onclick="popup();">here</a> to see some detailed information.
		</span>
	</div>
	<div id="error_details">
		<h1><%= exception.getClass().getName() %></h1>
		Message: <i>"<%= exception.getMessage() %>"</i><br>
		<br>
		
		<% StackTraceElement element = null;
		   Throwable cause = null;
		   
		   Throwable ex = exception;
		   do {
			   for (StackTraceElement e : ex.getStackTrace()) { 
			   		if (e.getClassName().startsWith("org.xrapla")) {
			   			element = e;
			   			cause = ex;
			   			break;
			   		}
			   } 
		   } while ((ex = ex.getCause()) != null); %>
		
		<% if (cause != null && element != null) { %> 
		
		 	Probably caused by: <b><%= cause.getClass().getName() %></b><br>
		 	<% if (cause.getMessage() != null) { %>
		 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>"<%= cause.getMessage() %>"</i><br>
		 	<% } %>
		 	at <b><%= element.getClassName() %>.<%= element.getMethodName() %>()</b> in <i><%= element.getFileName() %>(<%= element.getLineNumber() %>)</i><br>
	 	
	 	<% }
	 	   else { %>
	 	   
	 	   <i>Unable to determine any possible cause for the error.</i>
	 	   
	 	<% } %>
	 	<br>
	    
	 	<h2>Full stack trace:</h2>
 		<i><% exception.printStackTrace(new PrintWriter(out)); %></i>
	</div>
</body>