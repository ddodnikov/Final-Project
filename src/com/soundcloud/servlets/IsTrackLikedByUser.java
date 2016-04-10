package com.soundcloud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.TrackDAO;
import com.soundcloud.model.User;

@WebServlet("/IsTrackLikedByUser")
public class IsTrackLikedByUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int trackId = Integer.parseInt(request.getParameter("trackId"));
		int userId = ((User)request.getSession().getAttribute("currentUser")).getId();
		TrackDAO trackDao = TrackDAO.getTrackDAOInstance();
		boolean isTrackLiked = trackDao.isTrackLikedByUser(trackId, userId);
		boolean isTrackUnliked = trackDao.isTrackUnlikedByUser(trackId, userId);
		if (isTrackLiked) {
			String isTrackLikedResult = Boolean.toString(isTrackLiked);
			request.setAttribute("isTrackLiked", isTrackLikedResult);
			response.getWriter().write("trackIsLiked");
		}
		else if (isTrackUnliked) {
			String isTrackUnlikedResult = Boolean.toString(isTrackUnliked);
			request.setAttribute("isTrackUnliked", isTrackUnlikedResult);
			response.getWriter().write("trackIsUnliked");
		}
	}
}
