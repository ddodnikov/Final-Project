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

@WebServlet("/NextPreviousTrackPage")
public class NextPreviousTrackPage extends HttpServlet {
	private static final int SONGS_SHOWN = 5;
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

				
		if (request.getParameter("nextTracks") != null) {
			
			List<Track> tracksToDsiplay = TrackDAO.getTrackDAOInstance().getUserTracks(((User) request.getSession().getAttribute("currentUser")).getId(),
					(int) request.getSession().getAttribute("tracksShown") + SONGS_SHOWN);
			
			if(!tracksToDsiplay.isEmpty()) {
				
				for(int i = 0; i < tracksToDsiplay.size(); i++) {
					tracksToDsiplay.get(i).setIsLikedByUser(TrackDAO.getTrackDAOInstance().isTrackLikedByUser(
							tracksToDsiplay.get(i).getId(), ((User) request.getSession().getAttribute("currentUser")).getId()));
				}
				
				request.getSession().setAttribute("tracksToDisplay", tracksToDsiplay);
				request.getSession().setAttribute("tracksShown", (int)request.getSession().getAttribute("tracksShown") + SONGS_SHOWN);
			}
			
		}else {
			
			if(request.getParameter("previousTracks") != null) {
				
				if((int) request.getSession().getAttribute("tracksShown") >= SONGS_SHOWN) {
					List<Track> tracksToDsiplay = TrackDAO.getTrackDAOInstance().getUserTracks(((User) request.getSession().getAttribute("currentUser")).getId(),
						(int) request.getSession().getAttribute("tracksShown") - SONGS_SHOWN);
					
					request.getSession().setAttribute("tracksShown", (int)request.getSession().getAttribute("tracksShown") - SONGS_SHOWN);
					request.getSession().setAttribute("tracksToDisplay", tracksToDsiplay);
				}
				
			}
		}
		
		request.getSession().setAttribute("activeTab", "alltracks");
		request.getRequestDispatcher("./Home").forward(request, response);

	}

}
