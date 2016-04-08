package com.soundcloud.servlets;

import java.io.File;
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

	private static final String IMAGE_SAVE_DIR = "D:\\soundcloudFiles\\images";

	private static final int MAX_FILE_SIZE = 1024 * 1024 * 50;
			
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("currentUser") != null) {
			request.getRequestDispatcher("./createPlaylist.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("./");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		

		// creates the save directory if it does not exists
		File trackSaveDir = new File(IMAGE_SAVE_DIR);
		if (!trackSaveDir.exists()) {
			trackSaveDir.mkdir();
		}
		
		Part image = request.getPart("uploadPlaylistPic");

		String imageName = image.getSubmittedFileName();
		System.out.println(imageName);
		
			if(title.equals("")) {
				request.setAttribute("songErrorMessage", "The field title is required!");
			} else {
				if(image.getSize() > MAX_FILE_SIZE) {
					request.setAttribute("songErrorMessage", "Image exceeds maximum size limit of 50MB!");
				} else {
					int userId = (int) ((User)request.getSession().getAttribute("currentUser")).getId();
					image.write(IMAGE_SAVE_DIR + File.separator + imageName);
					ImageDAO.getImageDAOInstance().addImage(IMAGE_SAVE_DIR + File.separator + imageName);
					int img_id = ImageDAO.getImageDAOInstance().getImagIdByUri(IMAGE_SAVE_DIR + File.separator + imageName);
					PlaylistDAO.getPlaylistDAOInstance().addPlaylist(img_id, title, userId);
					request.setAttribute("songErrorMessage", "Your playlist was successfully created!");
				}
			}
		
		getServletContext().getRequestDispatcher("/createPlaylist.jsp").forward(request, response);

	}
	
}
