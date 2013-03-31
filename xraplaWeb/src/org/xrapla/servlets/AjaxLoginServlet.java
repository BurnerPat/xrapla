package org.xrapla.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xrapla.factory.BeanFactory;
import org.xrapla.handlers.UserHandler;
import org.xrapla.sessionbean.UserProviderLocal;

@WebServlet("/ajaxLogin")
public class AjaxLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
       
    public AjaxLoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = new PrintWriter(response.getOutputStream());
		out.print("failure");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = new PrintWriter(response.getOutputStream());
		
		String username = request.getParameter(USERNAME);
		String password = request.getParameter(PASSWORD);
		
		UserProviderLocal userProvider = BeanFactory.getUserProvider();
		UserHandler handler = new UserHandler(request.getSession(true), userProvider);
		
		if (username != null && password != null && handler.login(username, password)) {
			out.print("success");
		}
		else {
			out.print("failure");
		}
		
		out.close();
	}
}
