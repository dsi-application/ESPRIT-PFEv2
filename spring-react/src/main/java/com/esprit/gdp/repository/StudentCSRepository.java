package com.esprit.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.StudentCS;


@Repository
public interface StudentCSRepository extends JpaRepository<StudentCS, String>
{
	StudentCS findByToken(String token);

	@Query("Select u from StudentCS u where u.idEt=?1")
	Optional<StudentCS> findByIdEt(String idEt);
	
	@Query("Select u from StudentCS u where u.token =?1")
	Optional<StudentCS> findStudentByToken(String token);
	
	@Query("Select u.idEt,u.nomet,u.prenomet,u.adressemailesp ,u.telet from StudentCS u where u.idEt in " + 
			"(select y.id.idEt from InscriptionCS y where ( y.saisonClasse.id.codeCl like '4%' "
			+ "and y.saisonClasse.id.codeCl like CONCAT ('%', ?1, '%')))  ")  // anneeDeb
	List<Object[]> getEtudiantTerminalCS(String code);
	
	@Query("Select u.idEt,u.nomet,u.prenomet,u.adressemailesp ,u.telet from StudentCS u where u.idEt in " + 
			"(select y.id.idEt from InscriptionCS y where "
			+ "( y.saisonClasse.id.codeCl like '4%' and y.saisonClasse.id.codeCl like CONCAT ('%', ?1, '%'))"
			+ "or lower(y.saisonClasse.id.codeCl) like CONCAT('4cinfo-', ?1, '%') "
			+ "and y.saisonClasse.id.anneeDeb='2017'"
			+ ")  ")  // anneeDeb
	List<Object[]> getEtudiantTerminalCS2020(String code);
	
	@Query(value="select i.saisonClasse.id.codeCl from InscriptionCS i where i.id.idEt =:idET and i.saisonClasse.id.codeCl like '4%' and i.saisonClasse.id.codeCl not like '---' and i.saisonClasse.id.codeCl not like '----' and i.saisonClasse.id.codeCl not like '-----' order by i.id.anneeDeb desc")
	List<String> getOptionForEtudiantCS(@Param("idET") String idET);
	
//	@Query(value="SELECT e from StudentCS e, InscriptionCS i "
//			+ "where e.idEt = i.id.idEt and "
//			// oif + "on e.idEt = i.studentInscriptioncs.idEt where "
//			+ " i.encadrantPedagogique.idEns =:idEns and i.saisonClasse.id.codeCl like '4%'")
//	List<StudentCS> getEtudiantEncadrésCS(@Param("idEns") String idEns);
	
	@Query(value="SELECT e from StudentCS e join InscriptionCS i "
			+ "on e.idEt = i.studentInscriptioncs.idEt where "
			+ " ( i.encadrantPedagogiqueCS.idEns =:idEns and i.saisonClasse.id.codeCl like '4%' )")
	List<StudentCS> getEtudiantEncadrésCS(@Param("idEns") String idEns);
	
	@Query(value="SELECT i.id.idEt from InscriptionCS i "
			+ "where ( i.encadrantPedagogiqueCS.idEns =:idEns and i.saisonClasse.id.codeCl like '4%' and i.saisonClasse.id.anneeDeb=:annee)")
	List<String> getEtudiantEncadrésbyAnneeCS(@Param("idEns") String idEns,@Param("annee") String annee);
	
	
}
