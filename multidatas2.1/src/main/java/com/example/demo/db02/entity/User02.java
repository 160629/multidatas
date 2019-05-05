package com.example.demo.db02.entity;


import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user02")
public class User02 {
	/**
	 * 
	 */
	@Id
	private Integer userId;
	private String userName;
	private Integer userAge;
	private Integer userType;
	private Integer accountId;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}



}
