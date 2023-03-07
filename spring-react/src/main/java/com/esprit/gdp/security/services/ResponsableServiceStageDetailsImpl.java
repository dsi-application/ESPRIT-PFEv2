package com.esprit.gdp.security.services;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.esprit.gdp.models.ResponsableServiceStage;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponsableServiceStageDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;

	private String fullName;
	
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public ResponsableServiceStageDetailsImpl(String id, String adresse, String email) {
		this.id = id;
	}
	
	public ResponsableServiceStageDetailsImpl(String id, String fullName, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.fullName = fullName;
		this.password = password;
		this.authorities = authorities;
	}
	
	public ResponsableServiceStageDetailsImpl(String id, String password) {
		this.id = id;
		this.password = password;
	}

//	public static UserDetailsImpl build(User user)
//	{
//		
////		String combinedPassowrd = user.getCinpass();
////		String encodedPassword = combinedPassowrd.substring(0, combinedPassowrd.lastIndexOf("$$$$$"));
//		
//		return new UserDetailsImpl(
//				user.getIdUser(), 
//				user.getAddress(),
//				user.getEmail());
//	}

	public static ResponsableServiceStageDetailsImpl build(ResponsableServiceStage responsableServiceStage) {
		
		String combinedPassowrd = responsableServiceStage.getPwdJWTRSS();
		String encodedPassword = combinedPassowrd.substring(0, combinedPassowrd.lastIndexOf("$$$$$"));
		
		return new ResponsableServiceStageDetailsImpl(
				responsableServiceStage.getIdUserSce(),
				encodedPassword);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ResponsableServiceStageDetailsImpl rss = (ResponsableServiceStageDetailsImpl) o;
		return Objects.equals(id, rss.id);
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

}
