package com.example.demo.db01.view;


import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User01V", description = "用户对象")
public class User01V   {
    @ApiModelProperty(value = "用户id", name = "userId",example = "null")
	private Integer userId;
    @ApiModelProperty(value = "名称", name = "userName")
	private String userName;
    @ApiModelProperty(value = "年龄", name = "userAge",example = "0")
	private Integer userAge;
    @ApiModelProperty(value = "类型", name = "userType",example = "0")
	private Integer userType;
    @ApiModelProperty(value = "账号id", name = "accountId",example = "0")
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
