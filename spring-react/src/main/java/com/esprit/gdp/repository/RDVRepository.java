package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.FichePFETitreDateDepotStatusDto;
import com.esprit.gdp.dto.RendezVousVisiteStagiaireDto;
import com.esprit.gdp.models.RdvSuiviStage;
import com.esprit.gdp.models.RdvSuiviStagePK;


@Repository
public interface RDVRepository extends JpaRepository<RdvSuiviStage , RdvSuiviStagePK>
{
	
//	@Query(value="SELECT r from RdvSuiviStage r where r.rdvSuiviStagePK.fichePFEPK.conventionPK.idEt=?1 ")
//	List<RdvSuiviStage> findRdvsByStudent(String idEt);
	
//	@Query("Select new com.esprit.gdp.dto.TeacherDtoSTN(u.idEns, u.up, u.nomEns) from Teacher u where u.idEns=?1")
//	TeacherDtoSTN findTeacherById(String idEns);
	
//	@Query("SELECT new com.esprit.gdp.dto.RendezVousVisiteStagiaireDto(FUNCTION('to_char', r.dateRendezVous,'yyyy-mm-dd'), r.heureDebut, r.heureDebut, r.lieuRDV, r.observation, r.typeRDV) "
//			+ "from RdvSuiviStage r where r.rdvSuiviStagePK.fichePFEPK.conventionPK.idEt=?1 order by r.dateRendezVous asc")
//	List<RendezVousVisiteStagiaireDto> findRendezVousByStudent(String idEt);
	
	// rdvSuiviStages
	// select f.rdvSuiviStages from FichePFE f where f.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc
	/*
	 * 
	 * new com.esprit.gdp.dto.RendezVousVisiteStagiaireDto(
	 * FUNCTION('to_char', r.dateRendezVous,'yyyy-mm-dd'), 
	 * r.heureDebut, 
	 * r.heureDebut, 
	 * r.lieuRDV, 
	 * r.observation, 
	 * r.typeRDV) 
	 */
	
	// FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')
	@Query("SELECT new com.esprit.gdp.dto.RendezVousVisiteStagiaireDto('Rendez-Vous', FUNCTION('to_char', r.dateRendezVous,'yyyy-mm-dd'), r.heureDebut, '', r.lieuRDV, r.observation, cn.libNome, r.dateRendezVous) "
			+ "from RdvSuiviStage r, CodeNomenclature cn "
			+ "where r.rdvSuiviStagePK.fichePFEPK.conventionPK.idEt=?1 "
			+ "and FUNCTION('to_char', r.rdvSuiviStagePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS') =?2 "
			+ "and cn.cni.codeStr = '105' and cn.cni.codeNome = r.typeRDV "
			+ "order by r.dateRendezVous asc")
	List<RendezVousVisiteStagiaireDto> findRendezVousByStudent(String idEt, String dateDepotFichePFE);
	
//	@Query("select fp.rdvSuiviStages "
//			+ "from FichePFE fp where "
//			+ "fp.idFichePFE.conventionPK.idEt=?1 order by fp.idFichePFE.dateDepotFiche desc")
//	List<RdvSuiviStage> hi(String idEt);
	
	@Query("select new com.esprit.gdp.dto.FichePFETitreDateDepotStatusDto(fp.titreProjet, FUNCTION('to_char', fp.idFichePFE.dateDepotFiche,'dd-mm-yyyy'), cn.libNome) "
			+ "from FichePFE fp, CodeNomenclature cn "
			+ "where fp.idFichePFE.conventionPK.idEt=?1 "
			+ "and cn.cni.codeStr = '101' and cn.cni.codeNome = fp.etatFiche "
			+ "order by fp.idFichePFE.dateDepotFiche desc")
	List<FichePFETitreDateDepotStatusDto> findFicheTitreDateEtatByIdEt(String idEt);
	
	@Query(value="SELECT e from RdvSuiviStage e " + 
			"where ( FUNCTION('to_char', e.rdvSuiviStagePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:dateFiche and e.rdvSuiviStagePK.fichePFEPK.conventionPK.idEt =:idET ) order by e.dateRendezVous desc ")
	List<RdvSuiviStage> getRDVStagebyEt(@Param("idET") String idET, @Param("dateFiche") String dateFiche);
	
	@Query(value="SELECT e from  RdvSuiviStage e " + 
			"where ( FUNCTION('to_char', e.rdvSuiviStagePK.dateSaisieRendezVous,'yyyy-mm-dd HH24:MI:SS')=:dateSaisiRDV "
			+ "and e.rdvSuiviStagePK.fichePFEPK.conventionPK.idEt=:idET and  FUNCTION('to_char', e.rdvSuiviStagePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:datefiche)")
	RdvSuiviStage getRdvSuiviStagebyId(@Param("dateSaisiRDV") String dateSaisiRDV, @Param("datefiche") String datefiche,@Param("idET") String idET);
	
	
}
