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
    	HashSet<String> trackTags = (HashSet<String>) req.getSession().getAttribute("trackTags");
    	trackTags.add(currentTag);
    	req.getSession().setAttribute("trackTags", trackTags);
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
    	String spanText = br.readLine();
    	int splitIndex = spanText.indexOf('=');
    	String currentSpanText = spanText.substring(splitIndex + 1, spanText.length());
    	HashSet<String> trackTags = (HashSet<String>) req.getSession().getAttribute("trackTags");
    	trackTags.remove(currentSpanText);
    }
}
