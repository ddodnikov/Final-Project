package com.soundcloud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;

@WebServlet("/Charts")
public class Charts extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TrackDAO trackDao = new TrackDAO();
		List<Track> mostPlayedTracks = trackDao.getMostPlayedTracks(); // as json
		request.setAttribute("mostPlayedTracks", mostPlayedTracks);
		List<Track> mostLikedTracks = trackDao.getMostLikedTracks();
		request.setAttribute("mostLikedTracks", mostLikedTracks);
		List<Track> lastAddedTracks = trackDao.getLatestTracks();
		request.setAttribute("lastAddedTrack", lastAddedTracks);
	}
}