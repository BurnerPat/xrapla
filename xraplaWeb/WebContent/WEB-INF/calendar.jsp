<%@page import="org.xrapla.factory.BeanFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.DateFormatSymbols" %>
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
		<h1>My timetable</h1>
		<div class="widget" id="calendar">
			<div id="wrapper">
				<%	Calendar calendar = Calendar.getInstance(); 
					UserHandler userHandler = new UserHandler(request.getSession(true), BeanFactory.getUserProvider());
					UserCalendarHandler handler = new UserCalendarHandler(userHandler.getUser(), calendar);
				
					int minDay = 2;
					int numDays = 6;
					
					DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
					String dayNames[] = symbols.getWeekdays();
					
					int minHour = handler.getMinHour();
					int maxHour = handler.getMaxHour();%>
				<table>
					<thead>
						<tr>
							<th></th>
							<% for (int day = minDay; day < minDay + numDays; day++) { %>
								<th><%= dayNames[day % 8] %></th>
							<% } %>
						</tr>
					</thead>
					<tbody>
						<%	for (int i = minHour * 2; i <= maxHour * 2 + 1; i++) { %>
							<tr>
								<th><%= (i % 2 == 0) ? (((i / 2 <= 9) ? ("0" + i / 2) : i / 2) + ":00") : "" %></th>
								<% for (int day = minDay; day < minDay + numDays; day++) { 
										int actualDay = (day - minDay) % 7; %>
										<td></td>
								<% } %>
							</tr>
						<%	} %>
					</tbody>
				</table>
			</div>
		</div>
		<div id="branding">Powered by XRapla, &copy;2012</div>
	</body>
</html>