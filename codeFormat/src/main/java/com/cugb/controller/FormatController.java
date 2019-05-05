package com.cugb.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.cugb.util.*;
public class FormatController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String data=request.getParameter("code");
//		data=JavaForm.formJava(data);
		data=AppendBraceUtil.AppendBrace(data,"if");
		data=AppendBraceUtil.AppendBrace(data,"else");
		data=AppendBraceUtil.AppendBrace(data,"for");
		System.out.println(data);
		ModelAndView mav = new ModelAndView("formCode.jsp");
		mav.addObject("formCode", data);
		return mav;
	}

}
