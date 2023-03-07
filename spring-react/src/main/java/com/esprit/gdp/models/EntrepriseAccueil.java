/**
 * 
 */
package com.esprit.gdp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saria Essid
 *
 */


@Entity
@Table(name="ESP_ENTREPRISE_ACCUEUIL")
public class EntrepriseAccueil implements Serializable
{
	
	private static final long serialVersionUID = 4889020690136940564L;
	
	
	@TableGenerator(name = "Idt_entrepriseAccueil", table = "IDS_GEN_NEXT_VALUE", pkColumnName = "SEQUENCE_NAME", valueColumnName = "NEXT_VAL", pkColumnValue = "TBL_ENTREPRISE_ACCUEUIL", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Idt_entrepriseAccueil")
	@Column(name="ID_ENTREPRISE", length = 10)
	private Integer idEntreprise;
	
	@Column(name="DESIGNATION", length = 50)
	private String designation;
	
	@Column(name="ADDRESS", length = 250)
	private String address;

	@Column(name="SIEGE_SOCIALE", length = 100)
	private String siegeSocial;
	
	@Column(name="ADDRESS_MAIL", length = 100)
	private String addressMail;
	
	@Column(name="TELEPHONE", length = 50)
	private String telephone;
	
	@Column(name="FAX", length = 50)
	private String fax;
	
	
	
	
	
	//Convention
//	@OneToMany(mappedBy = "entrepriseAccueilConvention")
//    @JoinColumns({ @JoinColumn(name = "ID_ET", referencedColumnName = "ID_ET"),
//				   @JoinColumn(name = "DATE_CONVENTION", referencedColumnName = "DATE_CONVENTION") 
//				})
//	private List<Convention> conventions;
	
	
	@ManyToOne
	@JoinColumn(name = "ID_PAYS")
	private Pays pays;
	
	
	@ManyToOne
	@JoinColumn(name = "ID_SECTEUR")
	private SecteurEntreprise secteurEntreprise;
	
	
	@OneToMany(mappedBy = "entrepriseAccueilConvention", fetch=FetchType.LAZY) //, cascade=CascadeType.ALL)
    public List<Convention> conventions;
	
	
	public EntrepriseAccueil() {}
	
	public EntrepriseAccueil(Integer idEntreprise, String designation, String addressMail, String address, String telephone, String fax, Pays pays, SecteurEntreprise secteurEntreprise, String siegeSocial) {
		this.idEntreprise = idEntreprise;
		this.designation = designation;
		this.addressMail = addressMail;
		this.address = address;
		this.telephone = telephone;
		this.fax = fax;
		this.pays = pays;
		this.secteurEntreprise = secteurEntreprise;
		this.siegeSocial = siegeSocial;
	}

	public EntrepriseAccueil(String designation, String addressMail, String address, String telephone, String fax, Pays pays, SecteurEntreprise secteurEntreprise, String siegeSocial) {
		this.designation = designation;
		this.addressMail = addressMail;
		this.address = address;
		this.telephone = telephone;
		this.fax = fax;
		this.pays = pays;
		this.secteurEntreprise = secteurEntreprise;
		this.siegeSocial = siegeSocial;
	}
	
	/**************************************************** Getters & Setters **************************************************/


	public Integer getIdEntreprise() {
		return idEntreprise;
	}

	public void setIdEntreprise(Integer idEntreprise) {
		this.idEntreprise = idEntreprise;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSiegeSocial() {
		return siegeSocial;
	}

	public void setSiegeSocial(String siegeSocial) {
		this.siegeSocial = siegeSocial;
	}

	public String getAddressMail() {
		return addressMail;
	}

	public void setAddressMail(String addressMail) {
		this.addressMail = addressMail;
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

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}

	public SecteurEntreprise getSecteurEntreprise() {
		return secteurEntreprise;
	}

	public void setSecteurEntreprise(SecteurEntreprise secteurEntreprise) {
		this.secteurEntreprise = secteurEntreprise;
	}

	@JsonIgnore
	public List<Convention> getConventions() {
		return conventions;
	}

	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}

}

