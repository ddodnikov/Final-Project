package com.soundcloud.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.UserDAO;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");

		String password1 = request.getParameter("password1");

		String password2 = request.getParameter("password2");

		if (new UserDAO().isEmailUsed(email)) {

			request.setAttribute("usedEmailError", "This email is already registered!");

			if (password1.length() < 8) {

				request.setAttribute("shortPassword", "The password must be at least 8 characters!");

				if (!password1.equals(password2)) {

					request.setAttribute("differentPasswords", "The passwords don't match!");
				}
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);

		} else {
			response.sendRedirect("home.jsp");
		}

	}
}
