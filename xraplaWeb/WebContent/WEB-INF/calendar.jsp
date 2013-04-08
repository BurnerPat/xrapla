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
			<% int week, year;
			   
			   try {
				   week = Integer.parseInt(request.getParameter("week"));
				   year = Integer.parseInt(request.getParameter("year"));
			   }
			   catch (Exception ex) {
				   Calendar c = Calendar.getInstance();
				   week = c.get(Calendar.WEEK_OF_YEAR);
				   year = c.get(Calendar.YEAR);
			   }
			   %>
			<a class="previous" href="${pageContext.request.contextPath}/page?p=calendar&week=<%= (week - 1 > 0) ? (week - 1) : (52) %>&year=<%= (week - 1 > 0) ? year : (year - 1) %>">&lt;&lt;Previous</a>
			<select class="week" onchange="window.location.href='${pageContext.request.contextPath}/page?p=calendar&week='+this.options[this.selectedIndex].value+'&year=<%= year %>';">
				<% for (int i = 1; i <= 52; i++) { %>
					<option value="<%= i %>"<%= (i == week) ? " selected=\"selected\"" : "" %>>CW <%= i %></option>
				<% } %>
			</select>
			<input class="year" type="number" size="4" value="<%= year %>">
			<a class="next" href="${pageContext.request.contextPath}/page?p=calendar&week=<%= (week + 1 <= 52) ? (week + 1) : 0 %>&year=<%= (week + 1 <= 52) ? year : (year + 1) %>">Next&gt;&gt;</a>
		</div>
		<div class="widget" id="calendar">
			<div id="wrapper">
				<%	Calendar calendar = Calendar.getInstance(); 
					calendar.set(Calendar.WEEK_OF_YEAR, week);
					calendar.set(Calendar.YEAR, year);
					
					UserHandler userHandler = new UserHandler(request.getSession(true), BeanFactory.getUserProvider());
					UserCalendarHandler handler = new UserCalendarHandler(userHandler.getUser(), calendar);
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
					
					int minDay = 2;
					int numDays = 6;
					
					DateFormatSymbols symbols = new DateFormatSymbols(Locale.US);
					String dayNames[] = symbols.getWeekdays();
					
					int minHour = handler.getMinHour();
					int maxHour = handler.getMaxHour(); %>
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
						<%	for (int i = minHour * 2; i <= maxHour * 2 + 1; i++) {
							int actualHour = i / 2;%>
							<tr>
								<th><%= (i % 2 == 0) ? (((i / 2 <= 9) ? ("0" + i / 2) : i / 2) + ":00") : "" %></th>
								<% for (int day = minDay; day < minDay + numDays; day++) { 
									int actualDay = (day - minDay + 1) % 7; 
									
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
											<div class="appointment" style="height: <%= height %>%; margin-top: <%= margin %>px;">
												<div class="time">
													<% Date end = new Date(appointment.getTime().getTime() + appointment.getDuration() * 60 * 1000); %>
													<%= dateFormat.format(appointment.getTime()) %> - <%= dateFormat.format(end) %>
												</div>
												<div class="title"><%= appointment.getLecture().getName() %></div>
												<div class="docent">
													<% boolean br = !appointment.getLecture().getDocent().isEmpty();
													   for (Docent d : appointment.getLecture().getDocent()) { %>
														<%= d.getLastname() %>, <%= d.getFirstname() %>
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