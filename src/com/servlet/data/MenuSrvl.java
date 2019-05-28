package com.servlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.beans.dao.AreaDAO;
import com.beans.utils.DBConn;

/**
 * Servlet implementation class MenuSrvl
 */
@WebServlet("/MenuSrvl")
public class MenuSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuSrvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
		DBConn conn = new DBConn();
		JSONObject menuList = null;
		String sql = "SELECT MENU.MENU_ID, MENU.MENU_NAME, MENU.MENU_LINK, MENU_MAPPING.ACTIVE FROM USERS "+
					 "LEFT OUTER JOIN MENU_MAPPING ON USERS.ROLE_ID = MENU_MAPPING.ROLE_ID "+
					 "LEFT OUTER JOIN MENU ON MENU_MAPPING.MENU_ID = MENU.MENU_ID "+
					 "WHERE USERS.LOGIN_NAME = '"+request.getParameter("login_name")+"'";
		menuList = conn.getJsonArrayData(sql);
		out.println(menuList);
        out.close();
	}
}