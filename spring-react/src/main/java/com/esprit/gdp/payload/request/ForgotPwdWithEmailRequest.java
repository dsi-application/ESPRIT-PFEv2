package com.esprit.gdp.payload.request;

import javax.validation.constraints.NotBlank;

public class ForgotPwdWithEmailRequest
{
	
	@NotBlank
	private String email;
    

    
	/*********************** Getters & Setters ***********************/

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
        
}
