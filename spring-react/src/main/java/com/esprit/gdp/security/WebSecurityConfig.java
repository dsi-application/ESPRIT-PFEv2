package com.esprit.gdp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import com.esprit.gdp.security.jwt.JwtAuthEntryPoint;
import com.esprit.gdp.security.jwt.JwtAuthTokenPedagogicalCoordinatorFilter;
import com.esprit.gdp.security.jwt.JwtAuthTokenResponsableServiceStageFilter;
import com.esprit.gdp.security.jwt.JwtAuthTokenStudentCSFilter;
import com.esprit.gdp.security.jwt.JwtAuthTokenStudentFilter;
import com.esprit.gdp.security.jwt.JwtAuthTokenTeacherFilter;
import com.esprit.gdp.security.services.PedagogicalCoordinatorDetailsServiceImpl;
import com.esprit.gdp.security.services.ResponsableServiceStageDetailsServiceImpl;
import com.esprit.gdp.security.services.StudentDetailsCSServiceImpl;
import com.esprit.gdp.security.services.StudentDetailsServiceImpl;
import com.esprit.gdp.security.services.TeacherDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	ResponsableServiceStageDetailsServiceImpl responsableServiceStageDetailsService;
	
	@Autowired
	StudentDetailsServiceImpl studentDetailsService;

	@Autowired
	StudentDetailsCSServiceImpl studentDetailsCSService;

	@Autowired
	TeacherDetailsServiceImpl teacherDetailsService;

	@Autowired
	PedagogicalCoordinatorDetailsServiceImpl pedagogicalCoordinatorDetailsService;
	
	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public HttpFirewall defaultHttpFirewall() {
    	  // System.out.println("----------------------> lol a");
        return new DefaultHttpFirewall();
    }

    @Bean
	public JwtAuthTokenStudentFilter authenticationStudentJwtTokenFilter() {
		 // System.out.println("----------------------> lol 1.s");
		return new JwtAuthTokenStudentFilter();
	}
    
    @Bean
	public JwtAuthTokenStudentCSFilter authenticationStudentCSCJwtTokenFilter() {
		 // System.out.println("----------------------> lol 1.s");
		return new JwtAuthTokenStudentCSFilter();
	}
	
	@Bean
	public JwtAuthTokenResponsableServiceStageFilter authenticationResponsableServiceStageJwtTokenFilter() {
		 // System.out.println("----------------------> lol 1.r");
		return new JwtAuthTokenResponsableServiceStageFilter();
	}

	@Bean
	public JwtAuthTokenTeacherFilter authenticationTeacherJwtTokenFilter() {
		 // System.out.println("----------------------> lol 1.t");
		return new JwtAuthTokenTeacherFilter();
	}

	@Bean
	public JwtAuthTokenPedagogicalCoordinatorFilter authenticationPedagogicalCoordinatorJwtTokenFilter() {
		// // System.out.println("----------------------> lol 1.t");
		return new JwtAuthTokenPedagogicalCoordinatorFilter();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		 // System.out.println("----------------------> lol 2");
		authenticationManagerBuilder.userDetailsService(studentDetailsService).passwordEncoder(passwordEncoder());
		authenticationManagerBuilder.userDetailsService(studentDetailsCSService).passwordEncoder(passwordEncoder());
		authenticationManagerBuilder.userDetailsService(responsableServiceStageDetailsService).passwordEncoder(passwordEncoder());
		authenticationManagerBuilder.userDetailsService(teacherDetailsService).passwordEncoder(passwordEncoder());
		authenticationManagerBuilder.userDetailsService(pedagogicalCoordinatorDetailsService).passwordEncoder(passwordEncoder());
	}
	
//	protected void configure1(HttpSecurity http) throws Exception {
//	    http
//	        .authorizeRequests()
//	        .antMatchers("/admin/**").hasRole("ADMIN")
//	        .antMatchers("/publics/**").hasRole("USER") // no effect
//	        .anyRequest().authenticated();
//	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		  // System.out.println("----------------------> lol z");
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		  // System.out.println("----------------------> lol e");
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  // System.out.println("----------------------> lol r - Track 0");
		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		.antMatchers("/api/auth/**").permitAll()
		.antMatchers("/api/pedaCoord/**").permitAll()
		.antMatchers("/api/student/**").permitAll()
		.antMatchers("/api/academicEncadrant/**").permitAll()
		.antMatchers("/api/respServStg/**").permitAll()
		.antMatchers("/api/config/**").permitAll()
		.antMatchers("/api/encadrement/**").permitAll()
		.antMatchers("/api/responsableStage/**").permitAll()
		.antMatchers("/api/serviceStage/**").permitAll()
		.antMatchers("/api/test/**").permitAll()
		.antMatchers("/home/**").permitAll()
		.antMatchers("/spring-react/**").permitAll()
		.antMatchers("/simpleSigninStudent/**").permitAll()
	    .antMatchers("/**").permitAll()
	    .anyRequest().authenticated();

		http.addFilterBefore(authenticationStudentJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationStudentCSCJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationResponsableServiceStageJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationTeacherJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(authenticationPedagogicalCoordinatorJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

//  M2		http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
		
	}
	
	
	

//    M2
//    @Bean
//    public SessionRegistry sessionRegistry() {
//    	// System.out.println("----------------------> Track 1");
//        return new SessionRegistryImpl();
//    }
//
//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
//    	// System.out.println("----------------------> Track 2");
//        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
//    }
}
