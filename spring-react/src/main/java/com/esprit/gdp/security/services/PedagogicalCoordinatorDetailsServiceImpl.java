package com.esprit.gdp.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.models.PedagogicalCoordinator;
import com.esprit.gdp.repository.PedagogicalCoordinatorRepository;
import com.esprit.gdp.repository.TeacherRepository;

@Service
public class PedagogicalCoordinatorDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	PedagogicalCoordinatorRepository pedagogicalCoordinatorRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mailPC) throws UsernameNotFoundException {
		PedagogicalCoordinator pedagogicalCoordinator = pedagogicalCoordinatorRepository.findPedagogicalCoordinatorByLogin(mailPC)
				.orElseThrow(() -> new UsernameNotFoundException("PedagogicalCoordinator Not Found with id: " + mailPC));
		// System.out.println("-----------------------> lol 1");
		return PedagogicalCoordinatorDetailsImpl.build(pedagogicalCoordinator);
	}

}
