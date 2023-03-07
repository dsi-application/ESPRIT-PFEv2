package com.esprit.gdp.payload.request;

import java.util.Date;
import javax.validation.constraints.NotBlank;

 
public class AddAvenantRequest
{
 
	private Date dateSignatureConvention;

    private Date dateDebut;
    
    private Date dateFin;
        
	@NotBlank
    //@Size(min = 9, max = 20)
    private String numSiren;
	
	private String qualiteResponsable;
	
	private String responsableEntreprise;
	
	

    /*********************** Getters & Setters ***********************/
	
	
	public Date getDateDebut() {
		return dateDebut;
	}

	public Date getDateSignatureConvention() {
		return dateSignatureConvention;
	}

	public void setDateSignatureConvention(Date dateSignatureConvention) {
		this.dateSignatureConvention = dateSignatureConvention;
	}

	public String getNumSiren() {
		return numSiren;
	}

	public void setNumSiren(String numSiren) {
		this.numSiren = numSiren;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getResponsableEntreprise() {
		return responsableEntreprise;
	}

	public void setResponsableEntreprise(String responsableEntreprise) {
		this.responsableEntreprise = responsableEntreprise;
	}

	public String getQualiteResponsable() {
		return qualiteResponsable;
	}

	public void setQualiteResponsable(String qualiteResponsable) {
		this.qualiteResponsable = qualiteResponsable;
	}


}
