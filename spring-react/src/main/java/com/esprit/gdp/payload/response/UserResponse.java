package com.esprit.gdp.payload.response;

public class UserResponse
{
	private String id;
	private String password;
	
	public UserResponse() {}

	public UserResponse(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public UserResponse(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/************************************* JWT *****************************************/
	
	private String token;
	private String type = "Bearer";
	//private String code;
	
	/*public ParticpantResponse(String accessToken, String code, String address, String email, List<String> roles) {
		this.token = accessToken;
		this.code = code;
		this.address = address;
		this.email = email;
		this.roles = roles;
	}*/
	
	public UserResponse(String accessToken, String id, String password) {
		this.token = accessToken;
		this.id = id;
		this.password = password;
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
	
}
