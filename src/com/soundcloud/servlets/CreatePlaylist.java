package com.soundcloud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.soundcloud.model.ImageDAO;
import com.soundcloud.model.PlaylistDAO;
import com.soundcloud.model.User;

@WebServlet("/CreatePlaylist")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 0, // 0MB , always on hard
														// disk
		maxFileSize = 1024 * 1024 * 50, // 50MB
		maxRequestSize = 1024 * 1024 * 500) // 500MB
public class CreatePlaylist extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String SAVE_DIR = "D:\\soundcloudFiles\\";
	private static final String IMAGES = "\\images\\";

	private static final int MAX_FILE_SIZE = 1024 * 1024 * 50;
			
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) == null || request.getSession(false).getAttribute("currentUser") == null) {
			response.sendRedirect("./");
		} else {
			request.getRequestDispatcher("./createPlaylist.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = ((User)request.getSession().getAttribute("currentUser")).getEmailAddress();
		
		String title = request.getParameter("title");
		
		Part image = request.getPart("uploadPlaylistPic");

		String imageName = image.getSubmittedFileName();
		String imageDir = SAVE_DIR + email + IMAGES + imageName;
		
		
			if(title.equals("")) {
				request.setAttribute("songErrorMessage", "The field title is required!");
			} else {
				if(image.getSize() > MAX_FILE_SIZE) {
					request.setAttribute("songErrorMessage", "Image exceeds maximum size limit of 50MB!");
				} else {
					int userId = (int) ((User)request.getSession().getAttribute("currentUser")).getId();
					image.write(imageDir);
					ImageDAO.getImageDAOInstance().addImage(imageDir);
					int img_id = ImageDAO.getImageDAOInstance().getImagIdByUri(imageDir);
					PlaylistDAO.getPlaylistDAOInstance().addPlaylist(img_id, title, userId);
					request.setAttribute("songErrorMessage", "Your playlist was successfully created!");
				}
			}
		
		getServletContext().getRequestDispatcher("/createPlaylist.jsp").forward(request, response);

	}
	
}
