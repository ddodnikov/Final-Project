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
		
		if(request.getParameter("like") != null) {
			new TrackDAO().likeTrack(Integer.parseInt(request.getParameter("like")), 
					((User)request.getSession().getAttribute("currentUser")).getId());
		} else {
			if(request.getParameter("unlike") != null)
				new TrackDAO().unlikeTrack(Integer.parseInt(request.getParameter("unlike")), 
						((User)request.getSession().getAttribute("currentUser")).getId());
		}
		
	}

}
