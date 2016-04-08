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

@WebServlet("/NextPreviousLikedTrackPage")
public class NextPreviousLikedTrackPage extends HttpServlet {
	private static final int SONGS_SHOWN = 5;
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

				
		if (request.getParameter("nextLikedTracks") != null) {
			
			List<Track> likedTracksToDsiplay = TrackDAO.getTrackDAOInstance().getUserLikedTracks(((User) request.getSession().getAttribute("currentUser")).getId(),
					(int) request.getSession().getAttribute("likedTracksShown") + SONGS_SHOWN);

			if(!likedTracksToDsiplay.isEmpty()) {
				
				for(int i = 0; i < likedTracksToDsiplay.size(); i++)
					likedTracksToDsiplay.get(i).setIsLikedByUser(true);
				
				request.getSession().setAttribute("likedTracksToDisplay", likedTracksToDsiplay);
				
				request.getSession().setAttribute("likedTracksShown", (int)request.getSession().getAttribute("likedTracksShown") + SONGS_SHOWN);
			}
			
		}else {
			
			if(request.getParameter("previousLikedTracks") != null) {
				
				if((int) request.getSession().getAttribute("likedTracksShown") >= SONGS_SHOWN) {
					List<Track> likedTracksToDsiplay = TrackDAO.getTrackDAOInstance().getUserLikedTracks(((User) request.getSession().getAttribute("currentUser")).getId(),
						(int) request.getSession().getAttribute("likedTracksShown") - SONGS_SHOWN);
					
					request.getSession().setAttribute("likedTracksShown", (int)request.getSession().getAttribute("likedTracksShown") - SONGS_SHOWN);
					request.getSession().setAttribute("likedTracksToDisplay", likedTracksToDsiplay);
				}
				
			}
		}
		
		request.getSession().setAttribute("activeTab", "alllikes");
		response.sendRedirect("./Home");

	}

}
