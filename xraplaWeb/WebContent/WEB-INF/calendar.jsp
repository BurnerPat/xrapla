<%@page import="org.xrapla.factory.BeanFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.xrapla.entities.Appointment" %>
<%@ page import="org.xrapla.handlers.UserCalendarHandler" %>
<%@ page import="org.xrapla.handlers.UserHandler" %>
<!DOCTYPE html>
<html>
	<head>
		<title>CoolRapla</title>
		<meta charset="utf-8" />
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/calendar.css" />
	</head>
	<body>
		<h1>Vorlesungsplan</h1>
		<div class="widget" id="calendar">
			<%	Calendar calendar = Calendar.getInstance(); 
				UserHandler userHandler = new UserHandler(request.getSession(true), BeanFactory.getUserProvider());
				UserCalendarHandler handler = new UserCalendarHandler(userHandler.getUser(), calendar);
			
				int minDay = 2;
				int maxDay = 7; %>
			<table>
				<thead>
					<tr>
						<th></th>
						<% for (int day = minDay; day <= maxDay; day++) { %>
							<th><%= calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US) %>
						<% } %>
					</tr>
				</thead>
				<tbody>
					<%	for (int i = 0; i <= 23 * 2; i++) { %>
						<tr>
							<th><%= (i % 2 == 0) ? ((i < 9) ? ("0" + i) : i) : "" %></th>
							<% for (int day = minDay; day <= maxDay; day++) { %>
								<td></td>
							<% } %>
						</tr>
					<%	} %>
				</tbody>
			</table>
		</div>
		<div id="branding">Powered by CoolRapla, &copy;2012 CoolPeople
			with CoolSkillz</div>
	</body>
</html>