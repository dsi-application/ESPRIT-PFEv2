package com.esprit.gdp.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.models.Teacher;
import com.esprit.gdp.repository.TeacherRepository;

@Service
public class TeacherDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	TeacherRepository teacherRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mailEns) throws UsernameNotFoundException {
		Teacher teacher = teacherRepository.findTeacherByMailEns(mailEns.toLowerCase())
				.orElseThrow(() -> new UsernameNotFoundException("Teacher Not Found with id: " + mailEns));
		// System.out.println("-----------------------> lol 1");
		return TeacherDetailsImpl.build(teacher);
	}

}
