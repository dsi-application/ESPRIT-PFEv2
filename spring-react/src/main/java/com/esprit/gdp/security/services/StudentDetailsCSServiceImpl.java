package com.esprit.gdp.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.models.StudentCS;
import com.esprit.gdp.repository.SettingsRepository;
import com.esprit.gdp.repository.StudentRepository;

@Service
public class StudentDetailsCSServiceImpl implements UserDetailsService {
	
	@Autowired
	StudentRepository etudiantRepository;

	@Autowired
	SettingsRepository settingsRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException
	{
		List<String> activatedYears = settingsRepository.findActivatedYears();

		StudentCS etudiantCS = etudiantRepository.findCSStudentWithActivatedYears(id, activatedYears)
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found with id: " + id));

		return StudentCSDetailsImpl.build(etudiantCS);
	}

}
