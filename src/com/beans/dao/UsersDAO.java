package com.beans.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.beans.utils.DBConn;

public class UsersDAO {
	DBConn conn = null;
	String userId = "";
	String roleId = "";

	public UsersDAO(){
		conn = new DBConn();
	}	
	public boolean isExist(String userId, String password){
		boolean status = true;
		ArrayList<HashMap<String,String>> userObj = conn.getData("SELECT * FROM USERS WHERE LOGIN_NAME = '"+userId+"' AND PASSWORD = '"+password+"'");
		if(userObj.size()>0){
			status = true;
		}else{
			status = false;
		}
		return status;
	}
	public String getUserRole(String userId){
		String sql = "SELECT ROLE_ID FROM USERS WHERE LOGIN_NAME = '"+userId+"'";
		ArrayList<HashMap<String,String>> userObj = conn.getData(sql);
		if(userObj.size()>0){
			return userObj.get(0).get("ROLE_ID");
		}else{
			return "";
		}
	}
}
