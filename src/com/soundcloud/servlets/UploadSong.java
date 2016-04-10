package com.soundcloud.servlets;

import java.io.IOException;
import java.util.HashSet;
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

	private static final String SAVE_DIR = "D:\\soundcloudFiles\\";
	private static final String IMAGES = "\\images\\";
	private static final String TRACKS = "\\tracks\\";

	private static final int MAX_FILE_SIZE = 1024 * 1024 * 50;
			
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) == null || request.getSession(false).getAttribute("currentUser") == null) {
			response.sendRedirect("./");
		} else {
			Set<String> trackTags = new HashSet<String>();
			request.getSession().setAttribute("trackTags", trackTags);
			request.getRequestDispatcher("./uploadSong.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String email = ((User)request.getSession().getAttribute("currentUser")).getEmailAddress();
		String title = request.getParameter("title");
		String genre = request.getParameter("genre");
		String description = request.getParameter("description");
		
		int genreId = GenreDAO.getGenreDAOInstance().getIdByName(genre);

		Part track = request.getPart("songUpload");
		Part image = request.getPart("uploadTrackPic");

		String trackName = track.getSubmittedFileName();
		String imageName = image.getSubmittedFileName();
		
		String trackDir = SAVE_DIR + email + TRACKS + title + ".mp3";
		String imageDir = SAVE_DIR + email + IMAGES + imageName;
		
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
					Set<String> trackTags = (Set<String>) session.getAttribute("trackTags");
					track.write(trackDir);
					TrackDAO.getTrackDAOInstance().addTrack(title, genreId, description, trackDir, userId, trackTags);
					if(imageName.length() > 0) {
						image.write(imageDir);
						ImageDAO.getImageDAOInstance().addImage(imageDir);
						int img_id = ImageDAO.getImageDAOInstance().getImagIdByUri(imageDir);
						TrackDAO.getTrackDAOInstance().updateTrackImage(img_id, title);
					}
					request.setAttribute("songErrorMessage", "Your track was successfully uploaded!");
				}
			}
		}
		
		getServletContext().getRequestDispatcher("/uploadSong.jsp").forward(request, response);

	}
	
}
