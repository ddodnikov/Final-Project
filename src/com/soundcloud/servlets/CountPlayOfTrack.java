package com.soundcloud.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.TrackDAO;

@WebServlet("/CountPlayOfTrack")
public class CountPlayOfTrack extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int trackId = Integer.parseInt(request.getParameter("trackId"));
		TrackDAO trackDao = TrackDAO.getTrackDAOInstance();
		trackDao.addPlayToTrack(trackId);
	}
}
