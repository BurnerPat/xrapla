package org.xrapla.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xrapla.entities.Appointment;
import org.xrapla.factory.BeanFactory;

@WebServlet("/ajaxCreateAppointment")
public class AjaxCreateAppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AjaxCreateAppointmentServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int group = Integer.valueOf(request.getParameter("group"));
		int lecture = Integer.valueOf(request.getParameter("lecture"));
		String category = request.getParameter("category");

		Date date, time;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			date = dateFormat.parse(request.getParameter("date"));

			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			time = timeFormat.parse(request.getParameter("time"));
		}
		catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int duration = Integer.valueOf(request.getParameter("duration"));
		String roomSrc = request.getParameter("room");

		String room = roomSrc.split("-")[0];
		String wing = roomSrc.split("-")[1];
		int roomId = Integer.valueOf(room);

		if (category == null || room == null || wing == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		else {
			Appointment appointment = new Appointment();
			appointment.setCategory(category);
			appointment.setDuration(duration);
			appointment.setDate(date);
			appointment.setTime(time);

			appointment = BeanFactory.getAppointmentProvider().createAppointment(appointment, group, lecture, roomId);

			if (appointment == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			else {
				response.setStatus(HttpServletResponse.SC_OK);
			}
		}
	}

}
