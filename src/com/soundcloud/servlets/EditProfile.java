package com.soundcloud.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.soundcloud.model.DBConnection;
import com.soundcloud.model.UserDAO;
import com.soundcloud.model.User;

@WebServlet("/EditProfile")
@MultipartConfig
public class EditProfile extends HttpServlet {

	private static final String UPDATE_USER_BIOGRAPHY_QUERY = "UPDATE users SET biography = ? WHERE user_id = ?;";
	private static final String UPDATE_USER_COUNTRY_QUERY = "UPDATE users SET country = ? WHERE user_id = ?;";
	private static final String UPDATE_USER_CITY_QUERY = "UPDATE users SET city = ? WHERE user_id = ?;";
	private static final String UPDATE_USER_LAST_NAME_QUERY = "UPDATE users SET last_name = ? WHERE user_id = ?;";
	private static final String UPDATE_USER_FIRST_NAME_QUERY = "UPDATE users SET first_name = ? WHERE user_id = ?;";

	private static final Connection con = DBConnection.getDBInstance().getConnection();
	private static final String IMAGE_SAVE_DIR = "D:\\soundcloudFiles\\images";
	private static final int MAX_PICTURE_SIZE = 1024 * 1024 * 10; // 1 MB

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") == null) {
			response.sendRedirect("./");
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("./editProfile.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String biography = request.getParameter("biography");

		Part profilePicture = request.getPart("uploadProfilePic");
		Part headerImage = request.getPart("uploadHeaderPic");
		
		HttpSession session = request.getSession();
		int userId = ((User)session.getAttribute("currentUser")).getId();

		UserDAO userDao = new UserDAO();

		updateParameter(firstName, con, userId, UPDATE_USER_FIRST_NAME_QUERY);
		updateParameter(lastName, con, userId, UPDATE_USER_LAST_NAME_QUERY);
		updateParameter(city, con, userId, UPDATE_USER_CITY_QUERY);
		updateParameter(country, con, userId, UPDATE_USER_COUNTRY_QUERY);
		updateParameter(biography, con, userId, UPDATE_USER_BIOGRAPHY_QUERY);

		// check if the save directory exists - if not - create it
		File imageSaveDir = new File(IMAGE_SAVE_DIR);
		if (!imageSaveDir.exists()) {
			imageSaveDir.mkdir();
		}
		if (profilePicture != null) {
			if (profilePicture.getSize() > MAX_PICTURE_SIZE) {
				request.setAttribute("songErrorMessage",
						"Profile picture size exceeds maximum size limit of " + MAX_PICTURE_SIZE + "MB!");
			} else {
				String profilePictureName = profilePicture.getSubmittedFileName();
				if (profilePictureName.length() > 0) {
					profilePicture.write(IMAGE_SAVE_DIR + File.separator + profilePictureName);
					userDao.addImage(IMAGE_SAVE_DIR + File.separator + profilePictureName);
					int profilePicId = new UserDAO()
							.getImageByUri(IMAGE_SAVE_DIR + File.separator + profilePictureName);
					userDao.updateProfilePic(profilePicId, userId);
				}
			}
			if (headerImage != null) {
				if (headerImage.getSize() > MAX_PICTURE_SIZE) {
					request.setAttribute("songErrorMessage",
							"Header picture size exceeds maximum size limit of " + MAX_PICTURE_SIZE + "MB!");
				} else {
					String headerImageName = headerImage.getSubmittedFileName();
					if (headerImageName.length() > 0) {
						headerImage.write(IMAGE_SAVE_DIR + File.separator + headerImageName);
						userDao.addImage(IMAGE_SAVE_DIR + File.separator + headerImageName);
						int headerImageId = new UserDAO()
								.getImageByUri(IMAGE_SAVE_DIR + File.separator + headerImageName);
						userDao.updateHeaderPic(headerImageId, userId);
					}
				}
			}
			doGet(request, response);
		}
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
