package com.soundcloud.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import com.soundcloud.model.TrackDAO;

@WebServlet("/FetchTrackImage")
public class FetchTrackImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			TrackDAO trackDao = new TrackDAO();
			int currentTrackId = Integer.parseInt(request.getParameter("trackId"));
			String trackImageUri = trackDao.getTrackImageUri(currentTrackId);

			Path path = Paths.get(trackImageUri);
			String mime = Files.probeContentType(path);

			if (mime == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}

			response.setContentType(mime);
			File file = new File(trackImageUri);
			response.setContentLength((int) file.length());
			FileInputStream in = null;
			OutputStream out = null;
			in = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buf = new byte[1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}

			in.close();
			out.close();
	}

}
