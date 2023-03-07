package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.esprit.gdp.dto.EntrepriseAccueilDesignationPaysByStudentDto;
import com.esprit.gdp.models.EntrepriseAccueil;


@Repository
public interface EntrepriseAccueilRepository extends JpaRepository<EntrepriseAccueil, Integer>
{
	// Optional<EntrepriseAccueil> findByIdEntreprise(Integer idEntreprise);
	
	EntrepriseAccueil findByIdEntreprise(Integer idEntreprise);
	EntrepriseAccueil findByDesignation(String designation);
	
	@Query("Select ea.designation from EntrepriseAccueil ea order by ea.designation")
	List<String> findAllEntrepriseAccueil();
	
	@Query("Select new com.esprit.gdp.dto.EntrepriseAccueilDesignationPaysByStudentDto(ea.addressMail, ea.pays.nomPays) "
			+ "from EntrepriseAccueil ea, Convention c "
			+ "where c.entrepriseAccueilConvention.idEntreprise = ea.idEntreprise "
			+ "and c.conventionPK.idEt=?1 and c.traiter = '02'"
			+ "order by c.conventionPK.dateConvention asc")
	EntrepriseAccueilDesignationPaysByStudentDto findDesignationPaysByStudent(String idEt);
	
	@Query(value="SELECT e from EntrepriseAccueil e join Convention c on e.idEntreprise=c.entrepriseAccueilConvention.idEntreprise "
			+ "where c.conventionPK.idEt=:idET ")
	List<EntrepriseAccueil> getEntreprisebyEt(@Param("idET") String idET);

	//Boolean existsByIdResponsible(String idResponsible);

	//Boolean existsByEmail(String email);
	
//	@Query("Select u from Responsible u where u.idResponsible=?1 and u.pwdResponsible=?2")
//	Optional<Responsible> firstAuthentication(String idResponsible, String pwdResponsible);
//	
//	@Query("Select u from Responsible u where u.idResponsible=?1")
//	Optional<Responsible> forgotPassword(String idResponsible);
	
	/*
	@Query("Select u from User u where u.idUser=?1 and u.pwdJWTUser=?2")
	Optional<User> secondAuthentication(String idUser, String pwdJWTUser);
	*/
	
	/*
	@Query("Delete from EncadrantSociete e where e.email=?1")
	void deleteEncadrantSocieteByEmail(String email);
	*/
}
