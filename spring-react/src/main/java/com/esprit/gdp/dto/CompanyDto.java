package com.esprit.gdp.dto;

import java.util.List;

public class CompanyDto
{
	private Integer idEntreprise;
	
	private String designation;
		
	private String addressMail;
	
	private String address;
	
	private String telephone;
	
	private String fax;
	
	private String siegeSocial;
	
	private PaysDto paysDto;
	
	private SectorEntrepriseDto sectorEntrepriseDto;
	
	private List<ResponsibleDto> responsiblesDto;
	
	
	public CompanyDto() {}

	
	public CompanyDto(String designation) {
		this.designation = designation;
	}
	
	public CompanyDto(Integer idEntreprise, String designation) {
		this.idEntreprise = idEntreprise;
		this.designation = designation;
	}

	public CompanyDto(Integer idEntreprise, String designation, String addressMail, String address, String telephone, String fax, String siegeSocial, PaysDto paysDto, SectorEntrepriseDto sectorEntrepriseDto) {
		this.idEntreprise = idEntreprise;
		this.designation = designation;
		this.addressMail = addressMail;
		this.address = address;
		this.telephone = telephone;
		this.fax = fax;
		this.siegeSocial = siegeSocial;
		this.paysDto = paysDto;
		this.sectorEntrepriseDto = sectorEntrepriseDto;
	}
	
	public CompanyDto(Integer idEntreprise, String designation, String addressMail, String address, String telephone, String fax, String siegeSocial, PaysDto paysDto, SectorEntrepriseDto sectorEntrepriseDto, List<ResponsibleDto> responsiblesDto) {
		this.idEntreprise = idEntreprise;
		this.designation = designation;
		this.addressMail = addressMail;
		this.address = address;
		this.telephone = telephone;
		this.fax = fax;
		this.siegeSocial = siegeSocial;
		this.paysDto = paysDto;
		this.sectorEntrepriseDto = sectorEntrepriseDto;
		this.responsiblesDto = responsiblesDto;
	}

	public CompanyDto(Integer idEntreprise, String designation, String addressMail, String address, String telephone, String fax, String siegeSocial, PaysDto paysDto, List<ResponsibleDto> responsiblesDto)
	{
		this.idEntreprise = idEntreprise;
		this.designation = designation;
		this.addressMail = addressMail;
		this.address = address;
		this.telephone = telephone;
		this.fax = fax;
		this.siegeSocial = siegeSocial;
		this.paysDto = paysDto;
		this.responsiblesDto = responsiblesDto;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAddressMail() {
		return addressMail;
	}

	public void setAddressMail(String addressMail) {
		this.addressMail = addressMail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public PaysDto getPaysDto() {
		return paysDto;
	}

	public void setPaysDto(PaysDto paysDto) {
		this.paysDto = paysDto;
	}

	public Integer getIdEntreprise() {
		return idEntreprise;
	}

	public void setIdEntreprise(Integer idEntreprise) {
		this.idEntreprise = idEntreprise;
	}

	public SectorEntrepriseDto getSectorEntrepriseDto() {
		return sectorEntrepriseDto;
	}

	public void setSectorEntrepriseDto(SectorEntrepriseDto sectorEntrepriseDto) {
		this.sectorEntrepriseDto = sectorEntrepriseDto;
	}

	public String getSiegeSocial() {
		return siegeSocial;
	}

	public void setSiegeSocial(String siegeSocial) {
		this.siegeSocial = siegeSocial;
	}

	public List<ResponsibleDto> getResponsiblesDto() {
		return responsiblesDto;
	}

	public void setResponsiblesDto(List<ResponsibleDto> responsiblesDto) {
		this.responsiblesDto = responsiblesDto;
	}

	
}
