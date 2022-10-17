package com.docsupport.jp.pojo;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	private Credential user;

	public JwtResponse(String jwttoken,Credential user) {
		this.jwttoken = jwttoken;
		this.user = user;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public Credential getuser() {
		return user;
	}

	public void setuser(Credential user) {
		this.user = user;
	}
}