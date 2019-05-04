package com.cugb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/*
 * 
 */
public class DefaultController implements Controller {

public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
	// TODO Auto-generated method stub
	ModelAndView mav = new ModelAndView("index.jsp");
	//mav.addObject("message", "Hello Spring MVC");
	return mav;
	//return null;
}
	  
}
