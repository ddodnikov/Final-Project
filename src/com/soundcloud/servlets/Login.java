package com.soundcloud.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.hash.Hashing;
import com.soundcloud.model.DBConnection;
import com.soundcloud.model.UserDAO;

@WebServlet("/Login")
public class Login extends HttpServlet {

	private static final String GET_USER_ID_BY_EMAIL_QUERY = "SELECT user_id FROM users where email = ?;";
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession() != null) {
			request.getSession().invalidate();
		}
		request.getRequestDispatcher("./login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
		if (new UserDAO().isExistingUser(email, hashedPassword)) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", getCurrentUserId(email));
			request.getRequestDispatcher("./home.jsp").forward(request, response);
		} else {
			request.setAttribute("wrongUser", "Incorrect email or password!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
	}

	private int getCurrentUserId(String email) {
		Connection con = DBConnection.getDBInstance().getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(GET_USER_ID_BY_EMAIL_QUERY);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			int userId = 0;
			if (rs.next()) {
				// no validations required as this method is called only
				// when the user has logged in successfully
				userId = rs.getInt(1);
			}
			return userId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private void redirect(String destinationPage, HttpServletResponse response) throws IOException {
		String urlWithSessionID = response.encodeRedirectURL(destinationPage);
		response.sendRedirect(urlWithSessionID);
	}
}