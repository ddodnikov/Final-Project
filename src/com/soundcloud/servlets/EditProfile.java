package com.soundcloud.servlets;

import java.io.IOException;
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

import com.soundcloud.model.DBConnection;

@WebServlet("/EditProfile")
public class EditProfile extends HttpServlet {
	private static final String GET_IMG_URI_QUERY = "SELECT i.img_uri FROM images i INNER JOIN users u ON i.img_id = u.user_img_id WHERE user_id = ?;";
	
	private static final String UPDATE_USER_BIOGRAPHY_QUERY = "UPDATE users SET biography = ? WHERE user_id = ?;";
	private static final String UPDATE_USER_COUNTRY_QUERY = "UPDATE users SET country = ? WHERE user_id = ?;";
	private static final String UPDATE_USER_CITY_QUERY = "UPDATE users SET city = ? WHERE user_id = ?;";
	private static final String UPDATE_USER_LAST_NAME_QUERY = "UPDATE users SET last_name = ? WHERE user_id = ?;";
	private static final String UPDATE_USER_FIRST_NAME_QUERY = "UPDATE users SET first_name = ? WHERE user_id = ?;";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") == null)
		{
		   response.sendRedirect("./");
		   return;
		}
		// get the current logged in user's profile picture name
		Connection con = DBConnection.getDBInstance().getConnection();
		int userId = (int) request.getSession().getAttribute("userId");
		// TODO: validate if there is a logged in user
		// error page?
		try {
			PreparedStatement ps = con.prepareStatement(GET_IMG_URI_QUERY);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			request.setAttribute("currentProfilePic", rs.getString(1));
			request.getSession().setAttribute("currentProfilePic", rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("./editProfile.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String biography = request.getParameter("biography");
		
		Connection con = DBConnection.getDBInstance().getConnection();
		PreparedStatement ps;
		
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		
		updateParameter(firstName, con, userId, UPDATE_USER_FIRST_NAME_QUERY);
		updateParameter(lastName, con, userId, UPDATE_USER_LAST_NAME_QUERY);
		updateParameter(city, con, userId, UPDATE_USER_CITY_QUERY);
		updateParameter(country, con, userId, UPDATE_USER_COUNTRY_QUERY);
		updateParameter(biography, con, userId, UPDATE_USER_BIOGRAPHY_QUERY);
		
		doGet(request, response);
		// TODO: success page??
	}

	private void updateParameter(String parameter, Connection con, int userId, String query) {
		PreparedStatement ps;
		if (isParameterFilled(parameter)) {
			try {
				ps = con.prepareStatement(query);
				ps.setString(1, parameter);
				ps.setInt(2, userId);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
	}
	
	private boolean isParameterFilled(String parameter) {
		boolean isFilled = true;
		if (parameter == null || parameter.isEmpty()) {
			isFilled = false;
		}
		return isFilled;
	}

}
