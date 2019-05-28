package com.servlet.utils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.dao.UsersDAO;

/**
 * Servlet implementation class LoginSrvl
 */
@WebServlet("/LoginSrvl")
public class LoginSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginSrvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doget Servlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsersDAO userDao = new UsersDAO();
		if(userDao.isExist(request.getParameter("form_username"), request.getParameter("form_password"))){
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/menu.jsp?token="+request.getSession().getId()+"&role="+userDao.getUserRole(request.getParameter("form_username")));
			dispatcher.forward(request, response);			
		}else{
			response.sendRedirect("/pk/index.html");
		}
	}
}