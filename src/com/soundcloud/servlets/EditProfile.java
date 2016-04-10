package com.soundcloud.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.soundcloud.model.UserDAO;
import com.soundcloud.model.User;

@WebServlet("/EditProfile")
@MultipartConfig
public class EditProfile extends HttpServlet {

	private static final String SAVE_DIR = "D:\\soundcloudFiles\\";
	private static final String IMAGES = "\\images\\";
	private static final int MAX_PICTURE_SIZE = 1024 * 1024 * 10; // 5 MB

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession(false) == null || request.getSession(false).getAttribute("currentUser") == null) {
			response.sendRedirect("./Login");
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("./editProfile.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String email = ((User)request.getSession().getAttribute("currentUser")).getEmailAddress();

		String displayName = request.getParameter("displayName");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String biography = request.getParameter("biography");

		Part profilePicture = request.getPart("uploadProfilePic");
		Part headerImage = request.getPart("uploadHeaderPic");
		
		HttpSession session = request.getSession();
		int userId = ((User)session.getAttribute("currentUser")).getId();

		UserDAO userDao = UserDAO.getUserDAOInstance();
		userDao.updateUserInfo(userId, displayName, firstName, lastName, city, country, biography);
		
		String profilePictureName = profilePicture.getSubmittedFileName();
		String headerImageName = headerImage.getSubmittedFileName();
		String imageDir = SAVE_DIR + email + IMAGES + profilePictureName;
		String imageDir2 = SAVE_DIR + email + IMAGES + headerImageName;

		if (profilePicture != null) {
			if (profilePicture.getSize() > MAX_PICTURE_SIZE) {
				request.setAttribute("songErrorMessage",
						"Profile picture size exceeds maximum size limit of " + MAX_PICTURE_SIZE + "MB!");
			} else {
				if (profilePictureName.length() > 0) {
					profilePicture.write(imageDir);
					userDao.addImage(imageDir);
					int profilePicId = UserDAO.getUserDAOInstance()
							.getImageByUri(imageDir);
					userDao.updateProfilePic(profilePicId, userId);
				}
			}
			if (headerImage != null) {
				if (headerImage.getSize() > MAX_PICTURE_SIZE) {
					request.setAttribute("songErrorMessage",
							"Header picture size exceeds maximum size limit of " + MAX_PICTURE_SIZE + "MB!");
				} else {
					
					if (headerImageName.length() > 0) {
						headerImage.write(imageDir2);
						userDao.addImage(imageDir2);
						int headerImageId = UserDAO.getUserDAOInstance()
								.getImageByUri(imageDir2);
						userDao.updateHeaderPic(headerImageId, userId);
					}
				}
			}
			User user = userDao.getUserById(userId);		
			session.setAttribute("currentUser", user);
			response.sendRedirect("./Home");
		}
	}

}
