package com.fate.spring.mybatis.sql.entity;

import java.io.Serializable;

public class User  implements Serializable{
	
	private int id;
	private String userName;
	private String psw;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}

}
