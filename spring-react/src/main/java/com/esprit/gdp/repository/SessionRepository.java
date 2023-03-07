package com.esprit.gdp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Session;


@Repository
public interface SessionRepository extends JpaRepository<Session, Integer>
{
	Session findByIdSession(Integer idSession);
	
	@Query("select s.libelleSession from Session s where s.idSession =?1")
	String findLibelleSessionByIdSession(Integer idSession);
	
	@Query("select s.idSession from Session s where s.libelleSession =?1")
	Integer findIdSessionByLabelSession(String sessionLabel);
	
	@Query("select s.libelleSession from Session s order by s.dateDebut desc")
	List<String> findLibelleSessions();
	
}
