package com.soundcloud.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import javax.servlet.http.HttpSession;
=======
>>>>>>> 06a9d37bd715223ce15964e8d73b47fcfefb6dde
import javax.servlet.http.Part;

import com.soundcloud.model.TrackDAO;

@WebServlet("/UploadSong")
<<<<<<< HEAD
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 0, // 0MB , always on hard disk
=======
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 0, // 0MB , always on hard
														// disk
>>>>>>> 06a9d37bd715223ce15964e8d73b47fcfefb6dde
		maxFileSize = 1024 * 1024 * 50, // 50MB
		maxRequestSize = 1024 * 1024 * 500) // 500MB
public class UploadSong extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String SAVE_DIR = "D:\\soundcloudFiles\\tracks";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// creates the save directory if it does not exists
		File fileSaveDir = new File(SAVE_DIR);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		boolean hasFile = true;
		
		Part part = request.getPart("fileUpload");
		String fileName = extractFileName(part);

		if (!fileName.equals("") && part.getSize() < 50*1024*1024) {
			part.write(SAVE_DIR + File.separator + fileName);
		} else {
			hasFile = false;
		}
<<<<<<< HEAD

		if (hasFile) {
			HttpSession session = request.getSession();
			int userId = (int) session.getAttribute("userId");
			new TrackDAO().addTrack(fileName, "111", "222", SAVE_DIR + "\\" + fileName, userId);
			request.setAttribute("message", "Upload has been done successfully!");
		}
=======
		
		new TrackDAO().addTrack(fileName, "111", "222", SAVE_DIR + "\\" + fileName, 6);

		if (hasFile)
			request.setAttribute("message", "Upload has been done successfully!");
>>>>>>> 06a9d37bd715223ce15964e8d73b47fcfefb6dde
		else 
			request.setAttribute("message", "No file selected or file exceeds maximum size limit of 50MB!");

		getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
}
