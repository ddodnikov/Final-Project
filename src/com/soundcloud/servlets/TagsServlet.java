package com.soundcloud.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TagsServlet")
public class TagsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
    	String data = br.readLine();
    	int splitIndex = data.indexOf('=');
    	String currentTag = data.substring(splitIndex + 1, data.length());
    	System.out.println(currentTag);
    	Set<String> trackTags = (Set<String>) req.getSession().getAttribute("trackTags");
    	trackTags.add(currentTag);
    	req.getSession().setAttribute("trackTags", trackTags);
    }
}
