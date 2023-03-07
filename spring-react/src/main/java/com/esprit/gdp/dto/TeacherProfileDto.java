package com.esprit.gdp.dto;

public class TeacherProfileDto
{
	private String id;
	private String nom;
	private String phone;
	private String dateModifyJwtPwd;
	
	public TeacherProfileDto() {}
	
	
	public TeacherProfileDto(String id, String nom, String phone, String dateModifyJwtPwd)
	{
		this.id = id;
		this.nom = nom;
		this.phone = phone;
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}
	

	/************************************************ Getters & Setters *************************************************/


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDateModifyJwtPwd() {
		return dateModifyJwtPwd;
	}

	public void setDateModifyJwtPwd(String dateModifyJwtPwd) {
		this.dateModifyJwtPwd = dateModifyJwtPwd;
	}

}
