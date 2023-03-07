package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
 
public class AddPaysRequest
{
	
    @NotBlank
    @Size(min = 2, max = 2)
    private String codePays;
    
    @NotBlank
    @Size(min = 2, max = 50)
    private String nomPays;
 
    @NotBlank
    @Size(min = 3, max = 50)
    private String region;
    
    @NotBlank
    @Size(min = 3, max = 10)
    private String codePostal;

    
    
    /*********************** Getters & Setters ***********************/

    
	public String getCodePays() {
		return codePays;
	}

	public void setCodePays(String codePays) {
		this.codePays = codePays;
	}

	public String getNomPays() {
		return nomPays;
	}

	public void setNomPays(String nomPays) {
		this.nomPays = nomPays;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
    
	
}
