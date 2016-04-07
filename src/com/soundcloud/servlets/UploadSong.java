package com.soundcloud.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.soundcloud.model.GenreDAO;
import com.soundcloud.model.ImageDAO;
import com.soundcloud.model.TrackDAO;
import com.soundcloud.model.User;

@WebServlet("/UploadSong")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 0, // 0MB , always on hard
														// disk
		maxFileSize = 1024 * 1024 * 50, // 50MB
		maxRequestSize = 1024 * 1024 * 500) // 500MB
public class UploadSong extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String TRACK_SAVE_DIR = "D:\\soundcloudFiles\\tracks";
	private static final String IMAGE_SAVE_DIR = "D:\\soundcloudFiles\\images";

	private static final int MAX_FILE_SIZE = 1024 * 1024 * 50;
			
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("currentUser") != null) {
			request.getRequestDispatcher("./uploadSong.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("./");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		String genre = request.getParameter("genre");
		String description = request.getParameter("description");
		String[] tags = request.getParameterValues("tags");
		
		int genreId = new GenreDAO().getIdByName(genre);
		
		// creates the save directory if it does not exists
		File trackSaveDir = new File(TRACK_SAVE_DIR);
		if (!trackSaveDir.exists()) {
			trackSaveDir.mkdir();
		}
		
		File imageSaveDir = new File(IMAGE_SAVE_DIR);
		if (!imageSaveDir.exists()) {
			imageSaveDir.mkdir();
		}
		
		Part track = request.getPart("songUpload");
		Part image = request.getPart("uploadTrackPic");

		String trackName = track.getSubmittedFileName();
		String imageName = image.getSubmittedFileName();
		
		if (trackName.equals("") || track.getSize() > MAX_FILE_SIZE) {
			request.setAttribute("songErrorMessage", "No file selected or file exceeds maximum size limit of 50MB!");
		} else {
			if(title.equals("")) {
				request.setAttribute("songErrorMessage", "The field title is required!");
			} else {
				if(image.getSize() > MAX_FILE_SIZE) {
					request.setAttribute("songErrorMessage", "Image exceeds maximum size limit of 50MB!");
				} else {
					HttpSession session = request.getSession();
					int userId = (int) ((User)session.getAttribute("currentUser")).getId();
					Set<String> trackTags = (Set<String>) request.getAttribute("trackTags");
					track.write(TRACK_SAVE_DIR + File.separator + title + ".mp3");
					new TrackDAO().addTrack(title, genreId, description, TRACK_SAVE_DIR + File.separator + title + ".mp3", userId, trackTags);
					if(imageName.length() > 0) {
						image.write(IMAGE_SAVE_DIR + File.separator + imageName);
						new ImageDAO().addImage(IMAGE_SAVE_DIR + File.separator + imageName);
						int img_id = new ImageDAO().getImagIdByUri(IMAGE_SAVE_DIR + File.separator + imageName);
						new TrackDAO().updateTrackImage(img_id, title);
					}
					request.setAttribute("songErrorMessage", "Your track was successfully uploaded!");
				}
			}
		}
		
		getServletContext().getRequestDispatcher("/uploadSong.jsp").forward(request, response);

	}
	
}
