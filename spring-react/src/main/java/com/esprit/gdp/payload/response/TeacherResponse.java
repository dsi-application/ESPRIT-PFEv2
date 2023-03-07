package com.esprit.gdp.payload.response;

import java.util.List;

public class TeacherResponse
{
	private String id;
	private String password;
	private String idEns;
	private String codeOption;
	private Integer nbrAcademicEncadrement;
	private List<String> codeOptions;
	
	public TeacherResponse() {}

	public TeacherResponse(String id, String password, String idEns) {
		this.id = id;
		this.password = password;
		this.idEns = idEns;
	}
	
	public TeacherResponse(String id) {
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
	
	public String getIdEns() {
		return idEns;
	}

	public void setIdEns(String idEns) {
		this.idEns = idEns;
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
	
	public TeacherResponse(String accessToken, String id, String password, String idEns) {
		this.token = accessToken;
		this.id = id;
		this.password = password;
		this.idEns = idEns;
	}
	
	public TeacherResponse(String accessToken, String id, String password, String idEns, String codeOption, Integer nbrAcademicEncadrement, List<String> codeOptions) {
		this.token = accessToken;
		this.id = id;
		this.password = password;
		this.idEns = idEns;
		this.codeOption = codeOption;
		this.nbrAcademicEncadrement = nbrAcademicEncadrement;
		this.codeOptions = codeOptions;
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

	public String getCodeOption() {
		return codeOption;
	}

	public void setCodeOption(String codeOption) {
		this.codeOption = codeOption;
	}

	public Integer getNbrAcademicEncadrement() {
		return nbrAcademicEncadrement;
	}

	public void setNbrAcademicEncadrement(Integer nbrAcademicEncadrement) {
		this.nbrAcademicEncadrement = nbrAcademicEncadrement;
	}

	public List<String> getCodeOptions() {
		return codeOptions;
	}

	public void setCodeOptions(List<String> codeOptions) {
		this.codeOptions = codeOptions;
	}
	
	
}
