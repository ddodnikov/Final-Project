package com.soundcloud.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.hash.Hashing;
import com.soundcloud.model.UserDAO;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("./register.jsp");
		rd.forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");

		if (UserDAO.getUserDAOInstance().isEmailUsed(email)) {
			request.setAttribute("usedEmailError", "This email is already registered!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);

		} else {
			if (password1.length() < MIN_PASSWORD_LENGTH) {
				request.setAttribute("shortPassword", "The password must be at least 8 characters!");
				RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
				dispatcher.forward(request, response);
			
			} else {
				if (!password1.equals(password2)) {

					request.setAttribute("differentPasswords", "The passwords don't match!");
					RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
					dispatcher.forward(request, response);
				} else {
					String hashedPassword = Hashing.sha256()
					        .hashString(password1, StandardCharsets.UTF_8)
					        .toString();
					UserDAO.getUserDAOInstance().addUser(email, hashedPassword);
					request.getRequestDispatcher("./home.jsp").forward(request, response);
				}
			}
		}
	}
}
