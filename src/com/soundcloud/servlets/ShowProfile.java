package com.soundcloud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soundcloud.model.UserDAO;
import com.soundcloud.model.Track;
import com.soundcloud.model.TrackDAO;
import com.soundcloud.model.User;

@WebServlet("/home")
public class ShowProfile extends HttpServlet {
	private static final int SONGS_SHOWN = 5;
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession(false).getAttribute("currentUser") != null) {

			String id = request.getParameter("showUserId");
			int user_id = Integer.parseInt(id);
			
			if (((User)request.getSession().getAttribute("currentUser")).getId() == user_id) {
				response.sendRedirect("./home.jsp");
				return;
			}

			User user = new UserDAO().getUserById(user_id);
			request.getSession().setAttribute("showUser", user);
			
			List<Track> showTracks = new TrackDAO().getUserTracks(user_id, 0);
			request.getSession().setAttribute("showTracks", showTracks);
			request.getSession().setAttribute("userTracksShown", SONGS_SHOWN);

			request.getRequestDispatcher("./showUserInfo.jsp").forward(request, response);
		
		} else {
			response.sendRedirect("./");
		}
			
	}
}
