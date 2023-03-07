package com.esprit.gdp.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.models.StudentCJ;
import com.esprit.gdp.repository.SettingsRepository;
import com.esprit.gdp.repository.StudentRepository;

@Service
public class StudentDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	StudentRepository etudiantRepository;

	@Autowired
	SettingsRepository settingsRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException
	{
		List<String> activatedYears = settingsRepository.findActivatedYears();

		StudentCJ etudiant = etudiantRepository.findCJStudentWithActivatedYears(id, activatedYears)
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found with id: " + id));

		return StudentDetailsImpl.build(etudiant);
	}

}
