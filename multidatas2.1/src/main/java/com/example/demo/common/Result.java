package com.example.demo.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Result",description = "返回对象")
public class Result<T>{
	//响应码
	@ApiModelProperty(value = "响应码 ",name = "code",example="1")
	private Integer code;
	//响应信息
	@ApiModelProperty(value = "响应信息 ",name = "message")
	private String message;
	//时间戳 
	@ApiModelProperty(value = "时间戳",name= "time",example="1")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date time;
	//业务数据
	@ApiModelProperty(value = "业务数据 ",name = "data")
	private T data;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", time=" + time + ", data=" + data + "]";
	}
	public Result(Integer code, String message, Date time, T data) {
		super();
		this.code = code;
		this.message = message;
		this.time = time;
		this.data = data;
	}
	public Result() {
		super();
	}
	
	
	
}
