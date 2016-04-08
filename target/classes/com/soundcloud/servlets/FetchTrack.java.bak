package com.soundcloud.servlets;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;

@WebServlet("/FetchTrack")
public class FetchTrack extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TrackDAO trackDao = new TrackDAO();
		int currentTrackId = Integer.parseInt(request.getParameter("trackId"));
		Track currentTrack = trackDao.getTrackById(currentTrackId);
		String currentTrackUri = currentTrack.getTrackURL();
		Path path = Paths.get(currentTrackUri);
		String mime = Files.probeContentType(path);
		
		if (mime == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		response.setContentType(mime);
		File mp3File = new File(currentTrackUri);
		response.setContentLength((int) mp3File.length());
		
		FileInputStream in = new FileInputStream(mp3File);
		DataOutputStream out = new DataOutputStream(response.getOutputStream());

		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		out.flush();
		out.close();
		in.close();
	}
}
