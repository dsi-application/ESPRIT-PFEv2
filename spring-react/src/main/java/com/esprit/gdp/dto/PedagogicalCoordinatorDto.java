package com.esprit.gdp.dto;

import java.util.List;

public class PedagogicalCoordinatorDto
{
	
	private String numPhone;
	private String dateModifyJwtPwd;
	private String codeOption;
	private List<String> codeOptions;

	public PedagogicalCoordinatorDto() {}
	
	public PedagogicalCoordinatorDto(String numPhone, String dateModifyJwtPwd, String codeOption) {
		this.numPhone = numPhone;
		this.dateModifyJwtPwd = dateModifyJwtPwd;
		this.codeOption = codeOption;
	}

//	public PedagogicalCoordinatorDto(String fullName, String numPhone, String dateModifyJwtPwd, List<String> codeOptions) {
//		this.fullName = fullName;
//		this.numPhone = numPhone;
//		this.dateModifyJwtPwd = dateModifyJwtPwd;
//		this.codeOptions = codeOptions;
//	}


	/************************************************ Getters & Setters *************************************************/

	public String getNumPhone() {
		return numPhone;
	}

	public void setNumPhone(String numPhone) {
		this.numPhone = numPhone;
	}

	public String getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}

	public void setDateModifyJwtPwd(String dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

	public String getCodeOption() {
		return codeOption;
	}

	public void setCodeOption(String codeOption) {
		this.codeOption = codeOption;
	}

	public List<String> getCodeOptions() {
		return codeOptions;
	}

	public void setCodeOptions(List<String> codeOptions) {
		this.codeOptions = codeOptions;
	}
	
}
