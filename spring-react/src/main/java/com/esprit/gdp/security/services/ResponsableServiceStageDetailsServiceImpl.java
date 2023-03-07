package com.esprit.gdp.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.models.ResponsableServiceStage;
import com.esprit.gdp.repository.ResponsableServiceStageRepository;

@Service
public class ResponsableServiceStageDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	ResponsableServiceStageRepository responsableServiceStageRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		ResponsableServiceStage responsableServiceStage = responsableServiceStageRepository.findByIdUserSce(id)
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found with id: " + id));

		return ResponsableServiceStageDetailsImpl.build(responsableServiceStage);
	}

}
