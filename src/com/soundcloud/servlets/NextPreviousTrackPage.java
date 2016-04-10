package com.soundcloud.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
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
	private static final String SPLIT_REGEX = "[\\W]+";
	
	private void setLike(List<Track> tracks, int userId) {
		for(int i = 0; i < tracks.size(); i++)
			tracks.get(i).setIsLikedByUser(TrackDAO.getTrackDAOInstance().isTrackLikedByUser(
			tracks.get(i).getId(), userId));
	}
	
	private void setLikeTrue(List<Track> tracks){
		for(int i = 0; i < tracks.size(); i++)
			tracks.get(i).setIsLikedByUser(true);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String whichUserToShow = "";
		String whichTracksToShow = "";
		String whichCountToShow = "";
		RequestDispatcher rd = null;
		Set<String> tags = null;
		
		if(request.getParameter("nextTracks") != null || request.getParameter("previousTracks") != null) {
			whichUserToShow = "currentUser";
			whichTracksToShow = "tracksToDisplay";
			whichCountToShow = "tracksShown";
			request.getSession().setAttribute("activeTab", "alltracks");
			rd = request.getRequestDispatcher("./home.jsp");
		} else {
			if(request.getParameter("nextUserTracks") != null || request.getParameter("previousUserTracks") != null) {
				whichUserToShow = "showUser";
				whichTracksToShow = "showTracks";
				whichCountToShow = "userTracksShown";
				rd = request.getRequestDispatcher("/showUserInfo.jsp");
			} else {
				if(request.getParameter("nextLikedTracks") != null || request.getParameter("previousLikedTracks") != null) {
					whichUserToShow = "currentUser";
					whichTracksToShow = "likedTracksToDisplay";
					whichCountToShow = "likedTracksShown";
					request.getSession().setAttribute("activeTab", "alllikes");
					rd = request.getRequestDispatcher("./home.jsp");
				} else {
					if (request.getParameter("nextSearchTracks") != null || request.getParameter("previousSearchTracks") != null) {
						whichUserToShow = "currentUser";
						whichTracksToShow = "results";
						whichCountToShow = "songsShown";
						tags = new TreeSet<String>(Arrays.asList(((String)request.getSession().getAttribute("search")).split(SPLIT_REGEX)));
						rd = request.getRequestDispatcher("/searchResults.jsp");
					}
				}
			}
		}
		
		int userId = 0;
		if(request.getSession().getAttribute(whichUserToShow) != null)
			userId = ((User) request.getSession().getAttribute(whichUserToShow)).getId();
		int trackCount = (int) request.getSession().getAttribute(whichCountToShow);
		
		List<Track> tracks;
			
		if (request.getParameter("nextTracks") != null || request.getParameter("nextUserTracks") != null 
				|| request.getParameter("nextLikedTracks") != null || request.getParameter("nextSearchTracks") != null) {
			
			if(request.getParameter("nextLikedTracks") != null) {
				tracks = TrackDAO.getTrackDAOInstance().getUserLikedTracks(userId, trackCount + SONGS_SHOWN);
			}else {
				if(request.getParameter("nextSearchTracks") != null) {
					tracks = TrackDAO.getTrackDAOInstance().searchTracksTitleTagsAndGenreByWord(tags, trackCount + SONGS_SHOWN);
				} else {
					tracks = TrackDAO.getTrackDAOInstance().getUserTracks(userId, trackCount + SONGS_SHOWN);
				}
			}
			
			if(!tracks.isEmpty()) {
				
				if(request.getParameter("nextLikedTracks") != null) {
					setLikeTrue(tracks);
				} else {
					if(request.getParameter("nextSearchTracks") != null && request.getSession().getAttribute("currentUser") == null) {
					} else {
						setLike(tracks, userId);
					}
				}
				
				request.getSession().setAttribute(whichTracksToShow, tracks);
				request.getSession().setAttribute(whichCountToShow, trackCount + SONGS_SHOWN);
			}
			
		}else {
			
			if(request.getParameter("previousTracks") != null || request.getParameter("previousUserTracks") != null 
					|| request.getParameter("previousLikedTracks") != null || request.getParameter("previousSearchTracks") != null) {
				
				if(trackCount >= SONGS_SHOWN) {
					
					if(request.getParameter("previousLikedTracks") != null) {
						tracks = TrackDAO.getTrackDAOInstance().getUserLikedTracks(userId, trackCount - SONGS_SHOWN);
						setLikeTrue(tracks);
					} else {
						if(request.getParameter("previousSearchTracks") != null) {
							tracks = TrackDAO.getTrackDAOInstance().searchTracksTitleTagsAndGenreByWord(tags, trackCount - SONGS_SHOWN);
							if(request.getSession().getAttribute("currentUser") != null) {
								setLike(tracks, userId);
							}
						} else {
							tracks = TrackDAO.getTrackDAOInstance().getUserTracks(userId, trackCount - SONGS_SHOWN);
							setLike(tracks, userId);
						}
					}

					request.getSession().setAttribute(whichCountToShow, trackCount - SONGS_SHOWN);
					request.getSession().setAttribute(whichTracksToShow, tracks);
				}
				
			}
		}

		rd.forward(request, response);
	}
}
