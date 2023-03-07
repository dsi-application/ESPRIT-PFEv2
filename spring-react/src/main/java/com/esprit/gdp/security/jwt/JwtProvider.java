package com.esprit.gdp.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.esprit.gdp.security.services.PedagogicalCoordinatorDetailsImpl;
import com.esprit.gdp.security.services.ResponsableServiceStageDetailsImpl;
import com.esprit.gdp.security.services.StudentCSDetailsImpl;
import com.esprit.gdp.security.services.StudentDetailsImpl;
import com.esprit.gdp.security.services.TeacherDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${grokonez.app.jwtSecret}")
    private String jwtSecret;

    @Value("${grokonez.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateStudentJwtToken(Authentication authentication) {

    	StudentDetailsImpl studentPrincipal = (StudentDetailsImpl) authentication.getPrincipal();

    	// // System.out.println("---------------> Expiration Student: " + new Date());
    	
        return Jwts.builder()
		                .setSubject((studentPrincipal.getId()))
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
		                .signWith(SignatureAlgorithm.HS512, jwtSecret)
		                .compact();
    }
    

    public String generateStudentCSJwtToken(Authentication authentication) {

    	StudentCSDetailsImpl studentPrincipal = (StudentCSDetailsImpl) authentication.getPrincipal();

    	// // System.out.println("---------------> Expiration Student: " + new Date());
    	
        return Jwts.builder()
		                .setSubject((studentPrincipal.getId()))
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
		                .signWith(SignatureAlgorithm.HS512, jwtSecret)
		                .compact();
    }
    
    
    public String generateResponsableServiceStageJwtToken(Authentication authentication) {

    	ResponsableServiceStageDetailsImpl responsableServiceStagePrincipal = (ResponsableServiceStageDetailsImpl) authentication.getPrincipal();
    	
        return Jwts.builder()
		                .setSubject((responsableServiceStagePrincipal.getId()))
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
		                .signWith(SignatureAlgorithm.HS512, jwtSecret)
		                .compact();
    }
    
    public String generateTeacherJwtToken(Authentication authentication) {

    	TeacherDetailsImpl teacherPrincipal = (TeacherDetailsImpl) authentication.getPrincipal();

    	// // System.out.println("---------------> Expiration Teacher: " + new Date());
    	
        return Jwts.builder()
		                .setSubject((teacherPrincipal.getId()))
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
		                .signWith(SignatureAlgorithm.HS512, jwtSecret)
		                .compact();
    }
    

    public String generatePedagogicalCoordinatorJwtToken(Authentication authentication) {

    	PedagogicalCoordinatorDetailsImpl pedagogicalCoordinatorPrincipal = (PedagogicalCoordinatorDetailsImpl) authentication.getPrincipal();

    	// // System.out.println("---------------> Expiration Teacher: " + new Date());
    	
        return Jwts.builder()
		                .setSubject((pedagogicalCoordinatorPrincipal.getId()))
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
		                .signWith(SignatureAlgorithm.HS512, jwtSecret)
		                .compact();
    }
    
    
    public boolean validateJwtToken(String authToken) {
        try {
        	// // System.out.println("---------------> Expiration : " + new Date());
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        
        return false;
    }
    
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
			                .setSigningKey(jwtSecret)
			                .parseClaimsJws(token)
			                .getBody().getSubject();
    }
}