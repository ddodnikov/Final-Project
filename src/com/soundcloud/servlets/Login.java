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
import com.soundcloud.model.User;

@WebServlet("/Login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession(false) != null) {
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
			UserDAO userDao = new UserDAO();
			int currentUserId = userDao.getCurrentUserId(email);
			String currentProfilePicUri = userDao.getCurrentProfilePicUri(currentUserId);
			String currentHeaderPicUri = userDao.getHeaderImgUriByUserId(currentUserId);
			session.setAttribute("currentProfilePic", currentProfilePicUri);
			session.setAttribute("currentHeaderPic", currentHeaderPicUri);
			session.setAttribute("userId", currentUserId);
			User user = userDao.getUserById(currentUserId);
			
			session.setAttribute("currentUser", user);
			session.setAttribute("userName", user.getDisplayName());
			
			request.getRequestDispatcher("./home.jsp").forward(request, response);
		} else {
			request.setAttribute("wrongUser", "Incorrect email or password!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("./login.jsp");
			dispatcher.forward(request, response);
		}
	}

	
}