package com.esprit.gdp.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String address;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, String id, String address, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.address = address;
		this.email = email;
		this.roles = roles;
	}
	
	public JwtResponse(String accessToken, String id) {
		this.token = accessToken;
		this.id = id;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
