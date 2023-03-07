package com.esprit.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Pays;


@Repository
public interface PaysRepository extends JpaRepository<Pays, Integer>
{
	Optional<Pays> findByIdPays(String idPays);
	
	Pays findByCodePays(String codePays);
	
	// lower(trim(both from c.name))
	// @Query("Select p.nomPays from Pays p order by p.nomPays")
	@Query("Select p.nomPays from Pays p order by p.nomPays")
	List<String> findAllLibellePays();
	
	@Query("Select p from Pays p where p.nomPays like CONCAT('%', ?1, '%')")
	Pays findPaysByNamePays(String nomPays);
	
//	@Query("Select np.libelleNom from NomenclaturePays np where np.nomepays.nomPays=?1 order by np.libelleNom")
//	List<String> findStatesByPaysName(String nomPays);
	
	// @Query("Select np.libelleNom from NomenclaturePays np where np.abi.idPays=?1 order by np.libelleNom")
	@Query("Select np.libelleNom from NomenclaturePays np where np.abi.idPays=?1 order by np.libelleNom")
	List<String> findStatesByIdPays(Integer idPays);
	
	/*private Integer idPays;
	private String codePays;
	private String nomPays;
	private String region;
	private String codePostal;
	*/
	

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
