package com.esprit.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.dto.EntrepriseSupervisorDto;
import com.esprit.gdp.dto.TraitementFicheHistoryDto;
import com.esprit.gdp.models.EncadrantEntreprise;
import com.esprit.gdp.models.EncadrantEntreprisePK;


@Repository
public interface EncadrantEntrepriseRepository extends JpaRepository<EncadrantEntreprise, EncadrantEntreprisePK>
{
	
	List<EncadrantEntreprise> findByEmail(String email);

	Boolean existsByEmail(String email);
	
//	@Query("Select u from EncadrantEntreprise u where u.email=?1 and u.convention.conventionPK.idEt=?2")
//	EncadrantEntreprise findByEmailAndStudent(String email, String idEt);
	
	@Query("Select u from EncadrantEntreprise u where "
			+ "u.encadrantEntreprisePK.fichePFEPK.conventionPK.idEt=?2 and "
			+ "lower(trim(u.email)) =?1")
	EncadrantEntreprise findESByEmailAndStudent(String email, String idEt);
	
//	@Query("Select new com.esprit.gdp.dto.EntrepriseSupervisorDto(u.firstName, u.lastName, u.numTelephone, u.email) "
//			+ "from EncadrantEntreprise u, Convention c "
//			+ "where u.entrepriseAccueil.idEntreprise = c.entrepriseAccueilConvention.idEntreprise "
//			+ "and c.conventionPK.idEt=?1 and u.entrepriseAccueil.designation =?2")
//	List<EntrepriseSupervisorDto> findESByStudentAndCompany(String idEt, String compLabel);
	
	@Query("Select new com.esprit.gdp.dto.EntrepriseSupervisorDto(u.firstName, u.lastName, u.numTelephone, u.email) "
			+ "from EncadrantEntreprise u where "
			+ "u.encadrantEntreprisePK.fichePFEPK.conventionPK.idEt=?1 and "
			+ "FUNCTION('to_char', u.encadrantEntreprisePK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 "
			+ "order by u.encadrantEntreprisePK.idEncadrant asc")
	List<EntrepriseSupervisorDto> findEntrepriseSupervisorsByStudentAndFiche(String idEt, String dateDepotFiche);
	
	@Query("Select new com.esprit.gdp.dto.TraitementFicheHistoryDto("
			+ "FUNCTION('to_char', t.traitementFichePK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS'), "
			+ "FUNCTION('to_char', t.traitementFichePK.dateSaisieDemande,'dd-mm-yyyy HH24:MI:SS'), "
			+ "FUNCTION('to_char', t.dateTrtFiche,'dd-mm-yyyy HH24:MI:SS'), "
			+ "cn.libNome, t.typeTrtConv, t.etatTrt, t.description, t.motifRefus, t.pathAccordAnnulation) "
			+ "from TraitementFichePFE t, CodeNomenclature cn where "
			+ "t.traitementFichePK.fichePFEPK.conventionPK.idEt=?1 and "
			+ "FUNCTION('to_char', t.traitementFichePK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 and "
			+ "cn.cni.codeStr = '102' and cn.cni.codeNome= t.typeTrtFiche "
			+ "order by t.traitementFichePK.dateSaisieDemande desc")
	List<TraitementFicheHistoryDto> findTraitementFicheHistoryDtosByStudentAndFiche(String idEt, String dateDepotFiche);
	
	@Query("Select u from EncadrantEntreprise u where u.encadrantEntreprisePK.idEncadrant=?1 and u.pwdJWTEncadrant=?2")
	Optional<EncadrantEntreprise> firstAuthentication(String idResponsible, String pwdJWTEncadrant);
	
	@Query("Select u from EncadrantEntreprise u where u.encadrantEntreprisePK.idEncadrant=?1")
	Optional<EncadrantEntreprise> forgotPassword(String idResponsible);
	
	@Query("Select u.encadrantEntreprisePK.idEncadrant from EncadrantEntreprise u")
	List<String> findAllSupervisors();
	
	/*
	@Query("Select u from User u where u.idUser=?1 and u.pwdJWTUser=?2")
	Optional<User> secondAuthentication(String idUser, String pwdJWTUser);
	*/
	
	@Transactional
	@Modifying
	@Query("Delete from EncadrantEntreprise e where e.email=?1")
	void deleteEncadrantSocieteByEmail(String email);
	
	/*******************************NOOOOOOOOOOOOOOO*/
	@Query("Select new com.esprit.gdp.dto.EntrepriseSupervisorDto(u.firstName, u.lastName, u.numTelephone, u.email) "
			+ "from EncadrantEntreprise u where "
			+ "u.encadrantEntreprisePK.fichePFEPK.conventionPK.idEt=?1 "
			+ "order by u.encadrantEntreprisePK.fichePFEPK.dateDepotFiche desc")
	List<EntrepriseSupervisorDto> findEntrepriseSupervisorsByStudent(String idEt);
		
}
