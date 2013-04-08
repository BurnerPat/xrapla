<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DateFormatSymbols" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.xrapla.factory.BeanFactory"%>
<%@ page import="org.xrapla.entities.Appointment" %>
<%@ page import="org.xrapla.entities.Docent" %>
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
		<div id="weekselect">
			<% int day = -1, month = -1;
			
			   try {
				   day = Integer.parseInt(request.getParameter("day"));
				   month = Integer.parseInt(request.getParameter("month"));
			   }
			   catch (Exception ex) {
				   day = -1;
				   month = -1;
			   }
			   
			   int week, year;
			   
			   try {
				   year = Integer.parseInt(request.getParameter("year"));
			   
				   if (day > 0 && month > 0) {
					   Calendar c = Calendar.getInstance();
					   c.set(Calendar.DAY_OF_MONTH, day);
					   c.set(Calendar.MONTH, month - 1);
					   c.set(Calendar.YEAR, year);
					   
					   week = c.get(Calendar.WEEK_OF_YEAR);
				   }
				   else {
					   week = Integer.parseInt(request.getParameter("week"));
				   }
			   }
			   catch (Exception ex) {
				   Calendar c = Calendar.getInstance();
				   week = c.get(Calendar.WEEK_OF_YEAR);
				   year = c.get(Calendar.YEAR);
			   }
				   
			   Calendar calendar = Calendar.getInstance();
			   
			   int now = calendar.get(Calendar.WEEK_OF_YEAR);
			   
			   calendar.set(Calendar.WEEK_OF_YEAR, week);
			   calendar.set(Calendar.YEAR, year); 
			   
			   if (day < 0 || month < 0) {
				   day = calendar.get(Calendar.DAY_OF_MONTH);
				   month = calendar.get(Calendar.MONTH) + 1;
			   }
			   
			   DateFormatSymbols symbols = new DateFormatSymbols(Locale.US); %>
			   
			<a class="previous" href="${pageContext.request.contextPath}/page?p=calendar&week=<%= (week - 1 > 0) ? (week - 1) : (52) %>&year=<%= (week - 1 > 0) ? year : (year - 1) %>">&lt;&lt;Previous</a>
			<select id="week" onchange="window.location.href='${pageContext.request.contextPath}/page?p=calendar&week='+this.options[this.selectedIndex].value+'&year=<%= year %>';">
				<% for (int i = 1; i <= 52; i++) { %>
					<option value="<%= i %>"<%= (i == week) ? " selected=\"selected\"" : "" %>>CW <%= i %><%= (i == now) ? " (now)" : "" %></option>
				<% } %>
			</select>
			<input id="year" type="number" size="4" value="<%= year %>">
			<a class="next" href="${pageContext.request.contextPath}/page?p=calendar&week=<%= (week + 1 <= 52) ? (week + 1) : 0 %>&year=<%= (week + 1 <= 52) ? year : (year + 1) %>">Next&gt;&gt;</a><br>
			<span>Date select:</span>
			<select id="month">
				<% String[] monthNames = symbols.getMonths(); 
				   for (int i = 1; i <= 12; i++) { %>
					<option value="<%= i %>"<%= (i == month) ? " selected=\"selected\"" : "" %>><%= monthNames[i - 1] %></option>
				<% } %>
			</select>
			<select id="day">
				<% for (int i = 1; i <= 31; i++) { %>
					<option value="<%= i %>"<%= (i == day) ? " selected=\"selected\"" : "" %>><%= i %></option>
				<% } %>
			</select>
			<a href="#" class="go" onclick="window.location.href='${pageContext.request.contextPath}/page?p=calendar&day='+document.getElementById('day').value+'&month='+document.getElementById('month').value+'&year=<%= year %>';">Go &gt;&gt;&gt;</a>
		</div>
		<div class="widget" id="calendar">
			<div id="wrapper">
				<%	UserHandler userHandler = new UserHandler(request.getSession(true), BeanFactory.getUserProvider());
					UserCalendarHandler handler = new UserCalendarHandler(userHandler.getUser(), calendar);
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
					
					int minDay = 2;
					int numDays = 6;
		
					String dayNames[] = symbols.getWeekdays();
					
					int minHour = handler.getMinHour();
					int maxHour = handler.getMaxHour(); %>
				<table>
					<thead>
						<tr>
							<th></th>
							<% for (int d = minDay; d < minDay + numDays; d++) { %>
								<th><%= dayNames[d % 8] %></th>
							<% } %>
						</tr>
					</thead>
					<tbody>
						<%	for (int i = minHour * 2; i <= maxHour * 2 + 1; i++) {
							int actualHour = i / 2;%>
							<tr>
								<th><%= (i % 2 == 0) ? (((i / 2 <= 9) ? ("0" + i / 2) : i / 2) + ":00") : "" %></th>
								<% for (int d = minDay; d < minDay + numDays; d++) { 
									int actualDay = (d - minDay + 1) % 7; 
									
									Appointment appointment = null;
									for (Appointment a : handler.getDay(actualDay)) {
										Calendar c = Calendar.getInstance();
										c.setTime(a.getTime());
										
										if (c.get(Calendar.HOUR_OF_DAY) == actualHour) {
											int m = c.get(Calendar.MINUTE);
											
											if (m >= (i % 2) * 30 && m < (i % 2 + 1) * 30) {
												appointment = a;
												break;
											}
										}
									} %>
									<td>
										<% if (appointment != null) {
											int height = appointment.getDuration() * 100 / 60 * 2;
											Calendar c = Calendar.getInstance();
											c.setTime(appointment.getTime());
											int margin = (int)((double)(c.get(Calendar.MINUTE) % 30) / 30.0 * 32.0); %>
											<div class="appointment<%= appointment.getCategory().equalsIgnoreCase("exam") ? " exam" : "" %>" style="height: <%= height %>%; margin-top: <%= margin %>px;">
												<div class="time">
													<% Date end = new Date(appointment.getTime().getTime() + appointment.getDuration() * 60 * 1000); %>
													<%= dateFormat.format(appointment.getTime()) %> - <%= dateFormat.format(end) %>
												</div>
												<div class="title"><%= appointment.getLecture().getName() %></div>
												<div class="docent">
													<% boolean br = !appointment.getLecture().getDocent().isEmpty();
													   for (Docent e : appointment.getLecture().getDocent()) { %>
														<%= e.getLastname() %>, <%= e.getFirstname() %>
														<% if (br) { %>
															<br>
														<% } %>
													<% } %>
												</div>
												<div class="room"><%= String.valueOf(appointment.getRoom().getWing()) + String.valueOf(appointment.getRoom().getNumber()) %></div>
												<div class="category"><%= appointment.getCategory() %></div>
											</div>
										<% } %>
									</td>
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