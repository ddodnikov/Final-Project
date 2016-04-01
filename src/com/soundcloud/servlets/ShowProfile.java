package com.soundcloud.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.soundcloud.model.UserDAO;
import com.soundcloud.model.User;

@WebServlet("/index")
public class ShowProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("userid");
		int user_id = Integer.parseInt(id);
		
		HttpSession session = request.getSession();
		
		User user = new UserDAO().getUserById(user_id);
		session.setAttribute("user", user);
		
		session.setAttribute("firstNAME", user.getFirstName());
		session.setAttribute("lastNAME", user.getLastName());
		session.setAttribute("cityNAME", user.getCity());
		session.setAttribute("countryNAME", user.getCountry());
		session.setAttribute("pic", user.getUserImageURI());
		session.setAttribute("head", user.getHeaderImageURI());
		
		request.getRequestDispatcher("./showUserInfo.jsp").forward(request, response);
	}
}
