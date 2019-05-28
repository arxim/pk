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

import com.beans.dao.CustomerDAO;
import com.beans.dao.SupplierDAO;
import com.beans.utils.DBConn;
import com.beans.utils.JDate;

/**
 * Servlet implementation class SupplierSrvl
 */
@WebServlet("/SupplierSrvl")
public class SupplierSrvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SupplierDAO sup = null;
    public SupplierSrvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String,String> supplier = new HashMap<String, String>();
        DBConn db = null;
        String sql = "";
        JSONObject supId = new JSONObject();
        System.out.println(request.getParameter("method"));
        try{
	        if(request.getParameter("method").equals("fillform")){
	        	sql = "SELECT * FROM SUPPLIER WHERE SUP_ID = '"+request.getParameter("sup_id")+"'";
	        	db = new DBConn();
	        	out.println(db.getJsonData(sql));
	        }else if(request.getParameter("method").equals("getdata")){
	        	sql = "SELECT * FROM SUPPLIER WHERE SUP_ID = '"+request.getParameter("sup_id")+"'";
		        	db = new DBConn();
		        	out.println(db.getJsonData(sql));	        	
	        }else if(request.getParameter("method").equals("datatable")){
	        	sup = new SupplierDAO();
	            out.println(sup.getSupplierTable(""));        	
	        }else if(request.getParameter("method").equals("getid")){
	        	sql = "SELECT MAX(SUP_ID)+1 AS SUP_ID FROM SUPPLIER";
	        	db = new DBConn();
	        	System.out.println(sql);
	        	supId = db.getJsonData(sql);
	        	for(int i = supId.get("SUP_ID").toString().length(); i<5; i++){
	        		supId.put("SUP_ID", "0"+supId.get("SUP_ID"));
	        	}
	        	out.println(supId);
	        }else if(request.getParameter("method").equals("save")){
	        	System.out.println("METHOD : "+request.getParameter("method"));
	        	System.out.println("MODE : "+request.getParameter("mode"));
	        	sup = new SupplierDAO();
	        	supplier.put("SUP_ID", request.getParameter("sup_id"));
	        	supplier.put("SUP_NAME", request.getParameter("sup_name"));
	        	supplier.put("ADDRESS1", request.getParameter("address1"));
	        	supplier.put("ADDRESS2", request.getParameter("address2"));
	        	supplier.put("TELEPHONE", request.getParameter("telephone"));
	        	supplier.put("MOBILE", request.getParameter("mobile"));
	        	supplier.put("FAX", request.getParameter("fax"));
	        	supplier.put("TAX_ID", request.getParameter("tax_id"));
	        	supplier.put("PAYMENT_TERM", request.getParameter("payment_term").equals("") ? "0" : request.getParameter("payment_term") );
	        	supplier.put("REMARK", request.getParameter("remark"));
	        	if(request.getParameter("mode").equals("edit")){
	        		sup.updateData(supplier);
	        	}else if(request.getParameter("mode").equals("new")){
	        		sup.insertData(supplier);
	        	}else{}
	        	out.print("1");
	        }else{
	        	System.out.println("no method");
	        }
        }catch(Exception e){
        	System.out.println(e);
        }
        out.close();
	}
}