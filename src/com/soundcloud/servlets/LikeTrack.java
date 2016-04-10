package com.soundcloud.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.soundcloud.exceptions.TrackAlreadyLikedException;
import com.soundcloud.model.TrackDAO;
import com.soundcloud.model.User;

@WebServlet("/LikeTrack")
public class LikeTrack extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int trackId = Integer.parseInt(request.getParameter("trackId"));
		if(request.getParameter("like") != null) {
			try {
				TrackDAO.getTrackDAOInstance().likeTrack(Integer.parseInt(request.getParameter("like")), 
						((User)request.getSession().getAttribute("currentUser")).getId());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (TrackAlreadyLikedException e) {
				request.setAttribute("trackAlreadyLikedMsg", "You already like this track!");
				e.printStackTrace();
				return;
			}
		} else {
			if(request.getParameter("unlike") != null)
				try {
					TrackDAO.getTrackDAOInstance().unlikeTrack(Integer.parseInt(request.getParameter("unlike")), 
							((User)request.getSession().getAttribute("currentUser")).getId());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (TrackAlreadyLikedException e) {
					e.printStackTrace();
				}
		}
		// send the count of likes to ajax in song.jsp
		String trackLikes = Integer.toString(TrackDAO.getTrackDAOInstance().getTrackLikes(trackId));
		request.setAttribute("trackLikes", trackLikes);
		response.getWriter().write(trackLikes);
	}

}
