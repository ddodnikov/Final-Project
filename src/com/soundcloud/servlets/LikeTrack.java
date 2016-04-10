package com.soundcloud.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.TrackDAO;
import com.soundcloud.model.User;

@WebServlet("/LikeTrack")
public class LikeTrack extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int track_id;
		int currentUserId = ((User)request.getSession().getAttribute("currentUser")).getId();
		
		if(request.getParameter("like") != null) {
			track_id = Integer.parseInt(request.getParameter("like"));
			TrackDAO.getTrackDAOInstance().likeTrack(track_id, currentUserId);
		}else {
			track_id = Integer.parseInt(request.getParameter("unlike"));
			TrackDAO.getTrackDAOInstance().unlikeTrack(track_id, currentUserId);
		}
	}

}
