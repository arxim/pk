package com.beans.utils;

import com.beans.dao.UsersDAO;

public class VerifyLogin {
	private String userId = "";
	private String password = "";
	private String token = "";
	private UsersDAO userDao;
	
	public VerifyLogin(){
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String verifyUserLogin(){
		userDao.isExist(getUserId(), getPassword());
		return "";
	}
	
}
