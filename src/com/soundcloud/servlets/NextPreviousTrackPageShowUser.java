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
import com.soundcloud.model.User;

@WebServlet("/NextPreviousTrackPageShowUser")
public class NextPreviousTrackPageShowUser extends HttpServlet {
	private static final int SONGS_SHOWN = 5;
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		List<Track> showTracks = null;
				
		if (request.getParameter("next") != null) {
			
			showTracks = new TrackDAO().getUserTracks(((User) request.getSession().getAttribute("showUser")).getId(),
					(int) request.getSession().getAttribute("userTracksShown"));
			
			if(!showTracks.isEmpty()) {
				request.getSession().setAttribute("showTracks", showTracks);
				request.getSession().setAttribute("userTracksShown", (int)request.getSession().getAttribute("userTracksShown") + SONGS_SHOWN);
			}
			
		}else {
			
			if(request.getParameter("previous") != null) {
				
				if((int) request.getSession().getAttribute("userTracksShown") > SONGS_SHOWN) {
					showTracks = new TrackDAO().getUserTracks(((User) request.getSession().getAttribute("showUser")).getId(),
						(int) request.getSession().getAttribute("userTracksShown") - SONGS_SHOWN * 2);
					
					request.getSession().setAttribute("userTracksShown", (int)request.getSession().getAttribute("userTracksShown") - SONGS_SHOWN * 2);
					request.getSession().setAttribute("showTracks", showTracks);
				}
				
			}
		}
		
		request.getRequestDispatcher("/showUserInfo.jsp").forward(request, response);

	}

}
