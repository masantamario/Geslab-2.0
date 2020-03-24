package com.tfg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;


@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet{
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException{;
		 request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	 }
}
