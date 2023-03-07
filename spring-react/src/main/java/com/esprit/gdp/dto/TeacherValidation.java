package com.esprit.gdp.dto;

public class TeacherValidation {
	private String idEns;
	private String nomEns;
	private String prenomEns;
	
	private String mailEns;
	
	private int nbrencadrement;
	public String getIdEns() {
		return idEns;
	}
	public void setIdEns(String idEns) {
		this.idEns = idEns;
	}
	public String getNomEns() {
		return nomEns;
	}
	public void setNomEns(String nomEns) {
		this.nomEns = nomEns;
	}
	public String getPrenomEns() {
		return prenomEns;
	}
	public void setPrenomEns(String prenomEns) {
		this.prenomEns = prenomEns;
	}
	

	public String getMailEns() {
		return mailEns;
	}
	public void setMailEns(String mailEns) {
		this.mailEns = mailEns;
	}

	public int getNbrencadrement() {
		return nbrencadrement;
	}
	public void setNbrencadrement(int nbrencadrement) {
		this.nbrencadrement = nbrencadrement;
	}
	public TeacherValidation(String idEns, String nomEns,
			String mailEns,int nbrencadrement) {
		super();
		this.idEns = idEns;
		this.nomEns = nomEns;
		
		this.mailEns = mailEns;

		this.nbrencadrement = nbrencadrement;
	}
	public TeacherValidation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TeacherValidation(String idEns, String nomEns, int nbrencadrement) {
		super();
		this.idEns = idEns;
		this.nomEns = nomEns;
		this.nbrencadrement = nbrencadrement;
	}
	
}
