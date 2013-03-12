<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.xrapla.beans.Appointment" %>
<%@ page import="org.xrapla.classes.AppointmentProvider" %>
<!DOCTYPE html>
<head>
	<title>CoolRapla</title>
	<meta charset="utf-8"/>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/calendar.css"/>
</head>
<body>
	<h1>Vorlesungsplan</h1>
	<div class="widget" id="calendar">
		<%
			AppointmentProvider provider = new AppointmentProvider();
		%>
		<table>
			
		</table>
	</div>
	<div id="branding">Powered by CoolRapla, &copy;2012 CoolPeople with CoolSkillz</div>
</body>