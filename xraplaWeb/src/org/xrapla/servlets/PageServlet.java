package org.xrapla.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.openejb.server.httpd.HttpResponse;
import org.xrapla.factory.BeanFactory;
import org.xrapla.handlers.UserHandler;

@WebServlet("/page")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE = "p";
       
    public PageServlet() {        
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		UserHandler handler = new UserHandler(request.getSession(true), new BeanFactory().getUserProvider());
		
		if (handler.isLoggedIn()) {
			String page = request.getParameter(PAGE);
			
			if (page != null) {
				page = "/WEB-INF/" + page + ".jsp";
				
				request.getRequestDispatcher(page).forward(request, response);
			}
			else {
				response.sendError(HttpResponse.SC_NOT_FOUND);
			}
		}
		else {
			response.sendRedirect("login.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
