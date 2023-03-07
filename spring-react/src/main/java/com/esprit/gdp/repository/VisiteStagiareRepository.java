package com.esprit.gdp.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.esprit.gdp.dto.RendezVousVisiteStagiaireDto;
import com.esprit.gdp.models.VisiteStagiaire;
import com.esprit.gdp.models.VisiteStagiairePK;


@Repository
public interface VisiteStagiareRepository extends JpaRepository<VisiteStagiaire, VisiteStagiairePK>
{

//	@Query(value="SELECT v from VisiteStagiaire v where v.visiteStagiairePK.fichePFEPK.conventionPK.idEt=?1 ")
//	List<VisiteStagiaire> findVisiteStagiairesByStudent(String idEt);
	
	@Query("SELECT new com.esprit.gdp.dto.RendezVousVisiteStagiaireDto('Visite', FUNCTION('to_char', r.visiteStagiairePK.dateVisite,'yyyy-mm-dd'), r.heureDebut, r.heureFin, r.lieuVisite, r.observation, cn.libNome, r.visiteStagiairePK.dateVisite) "
			+ "from VisiteStagiaire r, CodeNomenclature cn "
			+ "where r.visiteStagiairePK.fichePFEPK.conventionPK.idEt=?1 "
			+ "and FUNCTION('to_char', r.visiteStagiairePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') =?2 "
			+ "and cn.cni.codeStr = '105' and cn.cni.codeNome = r.typeVisite "
			+ "order by r.visiteStagiairePK.dateVisite asc")
	List<RendezVousVisiteStagiaireDto> findVisiteStagiaireByStudent(String idEt, String dateDepotFichePFE);
	
//	@Query("SELECT new com.esprit.gdp.dto.RendezVousVisiteStagiaireDto(FUNCTION('to_char', r.visiteStagiairePK.dateVisite,'dd-mm-yyyy'), r.heureDebut, r.heureFin, r.lieuVisite, r.observation, r.typeVisite) "
//			+ "from RdvSuiviStage r "
//			+ "where r.rdvSuiviStagePK.fichePFEPK.conventionPK.idEt=?1 "
//			+ "and FUNCTION('to_char', r.rdvSuiviStagePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') =?2 "
//			+ "order by r.dateRendezVous asc")
//	List<RendezVousVisiteStagiaireDto> findRendezVousByStudent(String idEt, String dateDepotFichePFE);
	
	@Query(value="SELECT v from  VisiteStagiaire v " + 
			"where ( FUNCTION('to_char', v.visiteStagiairePK.dateVisite,'dd-mm-yyyy')=:dateVisite "
			+ " and v.visiteStagiairePK.fichePFEPK.conventionPK.idEt=:idET "
			+ " and FUNCTION('to_char', v.visiteStagiairePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:datefiche)")
	Optional<VisiteStagiaire> getVisiteStagiarebyId(@Param("dateVisite") String dateVisite,@Param("datefiche") String datefiche,@Param("idET") String idET);
	
	@Query(value="SELECT c.LIB_NOME FROM SYN_CODE_NOMENCLATURE c , ESP_FICHE_PFE f  , ESP_VISITE_STAGIAIRE v "
			+ "WHERE (v.ID_ET = f.ID_ET "
			+ "AND  f.ID_ET =:idET "
			+ "AND  TO_CHAR(f.DATE_DEPOT_FICHE, 'DD-MON-YYYY HH24:MI:SS')=:datefiche "
			+ "AND  v.DATE_VISITE=:dateVisite "
			+ "AND c.CODE_NOME= v.TYPE_VISITE "
			+ "AND c.CODE_STR =105)",
			 nativeQuery = true)
	String getVisiteEtat(@Param("dateVisite") Date dateVisite,@Param("datefiche") Date datefiche,@Param("idET") String idET);
	
	@Query(value="SELECT DISTINCT  v from VisiteStagiaire v "
			+ "join StudentCJ s on s.idEt=v.visiteStagiairePK.fichePFEPK.conventionPK.idEt " + 
			"join InscriptionCJ i  on  s.idEt=i.id.idEt "
			+ "join FichePFE f on v.visiteStagiairePK.fichePFEPK.conventionPK.idEt=i.id.idEt " + 
			" where i.encadrantPedagogique.idEns =:idEns  and f.etatFiche NOT IN ('01','02','04','05') order by v.visiteStagiairePK.dateVisite desc ")
	List<VisiteStagiaire> getVisitlistForEns(@Param("idEns") String idEns);
	
//	@Query(value="SELECT v from VisiteStagiaire v " + 
//			"    join StudentCS e   on v.visiteStagiairePK.fichePFEPK.conventionPK.idEt =e.idEt " + 
//			"    join InscriptionCS i  on  e.idEt=i.id.idEt " + 
//			"			where  i.encadrantPedagogique.idEns =:idEns order by v.visiteStagiairePK.dateVisite desc ")
//	List<VisiteStagiaire> getVisitlistForEnsCS(@Param("idEns") String idEns);
	
	@Query(value="SELECT DISTINCT  v from VisiteStagiaire v "
			+ "join StudentCS s on s.idEt=v.visiteStagiairePK.fichePFEPK.conventionPK.idEt " + 
			"join InscriptionCS i  on  s.idEt=i.id.idEt "
			+ "join FichePFE f on v.visiteStagiairePK.fichePFEPK.conventionPK.idEt=i.id.idEt " + 
			" where i.encadrantPedagogiqueCS.idEns =:idEns  and f.etatFiche NOT IN ('01','02','04','05') order by v.visiteStagiairePK.dateVisite desc ")
	List<VisiteStagiaire> getVisitlistForEnsCS(@Param("idEns") String idEns);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE VisiteStagiaire v "+ 
			"	SET v.visiteStagiairePK.dateVisite=:newdate "+
			"	WHERE  FUNCTION('to_char', v.visiteStagiairePK.dateVisite,'dd-mm-yyyy')=:oldDate "
			+ "and  v.visiteStagiairePK.fichePFEPK.conventionPK.idEt=:idET "
			+ "and FUNCTION('to_char', v.visiteStagiairePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:DateFiche ")
	int updateVisite(@Param("oldDate") String oldDate ,@Param("idET") String idET,@Param("DateFiche") String DateFiche ,@Param("newdate") Date newdate);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM VisiteStagiaire v WHERE (FUNCTION('to_char', v.visiteStagiairePK.dateVisite,'dd-mm-yyyy')=:dateVisite "
			+ " and v.visiteStagiairePK.fichePFEPK.conventionPK.idEt=:idET "
			+ " and FUNCTION('to_char', v.visiteStagiairePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:datefiche) ")
	int deleteVisite(@Param("dateVisite") String dateVisite, @Param("datefiche") String datefiche,@Param("idET")  String idET);
	
	

	@Query(value="SELECT v from  VisiteStagiaire v " + 
			"where (  "
			+ "  v.visiteStagiairePK.fichePFEPK.conventionPK.idEt=:idET "
			+ " and FUNCTION('to_char', v.visiteStagiairePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:datefiche)")
	List<VisiteStagiaire> getVisiteStagiarebyEtudiant(@Param("datefiche") String datefiche,@Param("idET") String idET);
}
