package com.esprit.gdp.repository;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.TraitementFicheTypeEtatDto;
import com.esprit.gdp.models.TraitementFichePFE;
import com.esprit.gdp.models.TraitementFichePK;


@Repository
public interface TraitementFicheRepository extends JpaRepository<TraitementFichePFE, TraitementFichePK>
{
	
	@Query("select a.description from TraitementFichePFE a where a.traitementFichePK.fichePFEPK.conventionPK.idEt =?1")
	List<String> findAllAnnulationFiches(String idEt);
	
	@Query("select fp.description, fp.etatTrt, "
			+ "FUNCTION('to_char', fp.traitementFichePK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS'), "
			+ "FUNCTION('to_char', fp.traitementFichePK.dateSaisieDemande,'dd-mm-yyyy HH24:MI:SS'), fp.typeTrtConv "
			+ "from TraitementFichePFE fp "
			+ "where fp.traitementFichePK.fichePFEPK.conventionPK.idEt=?1 and "
			+ "FUNCTION('to_char', fp.traitementFichePK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS') =?2 "
			+ "order by fp.traitementFichePK.dateSaisieDemande asc")
	List<Object[]> findAnnulationFichePFEByStudentAndFichePFE(String idEt, String dateDepotFiche);
	
	@Query("select new com.esprit.gdp.dto.TraitementFicheTypeEtatDto(fp.typeTrtFiche, fp.typeTrtConv, fp.etatTrt) "
			+ "from TraitementFichePFE fp "
			+ "where fp.traitementFichePK.fichePFEPK.conventionPK.idEt=?1 "
			+ "order by fp.traitementFichePK.fichePFEPK.dateDepotFiche desc, fp.traitementFichePK.dateSaisieDemande desc")
			// + "order by fp.traitementFichePK.fichePFEPK.dateDepotFiche desc")
	List<TraitementFicheTypeEtatDto> findTraitementFicheTypeEtat(String idEt);
	
	@Query("select fp.traitementFichePK.dateSaisieDemande from "
			+ "TraitementFichePFE fp where "
			+ "fp.traitementFichePK.fichePFEPK.conventionPK.idEt=?1 and "
			+ "fp.traitementFichePK.fichePFEPK.dateDepotFiche =?2 and "
			+ "fp.typeTrtFiche = '02' "
			+ "order by fp.traitementFichePK.dateSaisieDemande desc")
	List<Timestamp> findDateRemiseFichePFEByStudentAndFiche(String idEt, Timestamp dateDepotFiche);
	
	@Query("select fp.traitementFichePK.dateSaisieDemande from "
			+ "TraitementFichePFE fp where "
			+ "fp.traitementFichePK.fichePFEPK.conventionPK.idEt=?1 and "
			+ "FUNCTION('to_char', fp.traitementFichePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS.FF9') =?2 and "
			+ "fp.typeTrtFiche = '02' "
			+ "order by fp.traitementFichePK.dateSaisieDemande desc")
	List<Timestamp> findDateRemiseFichePFEByStudentAndFicheSTR(String idEt, String dateDepotFiche);
	
	
	@Query("select t "
			+ "from TraitementFichePFE t where "
			+ "t.traitementFichePK.fichePFEPK.conventionPK.idEt=?1 and "
			+ "FUNCTION('to_char', t.traitementFichePK.fichePFEPK.dateDepotFiche,'dd-mm-yyyy HH24:MI:SS')=?2 and "
			+ "FUNCTION('to_char', t.traitementFichePK.dateSaisieDemande,'dd-mm-yyyy HH24:MI:SS')=?3")
	TraitementFichePFE findTraitementFicheByStudentAndFicheAndDateSaisieDemandeTrt(String idEt, String dateDepotFiche, String dateSaisieDemandeTrt);
	
	@Query(value="SELECT t from TraitementFichePFE t "
			+ "where t.traitementFichePK.fichePFEPK.conventionPK.idEt=:idET "
			+ "and FUNCTION('to_char', t.traitementFichePK.fichePFEPK.dateDepotFiche,'yyyy-mm-dd HH24:MI:SS')=:dateDepotFiche "
			+ "and FUNCTION('to_char', t.traitementFichePK.dateSaisieDemande,'yyyy-mm-dd HH24:MI:SS')=:dateTrtFiche "
			// oif + "and FUNCTION('to_char', t.traitementFichePK.dateSaisieDemande,'dd-mm-yyyy')=:dateTrtFiche "
			)
	Optional<TraitementFichePFE> getTraitementFichePFEById(@Param("idET") String idET ,@Param("dateDepotFiche") String dateDepotFiche ,@Param("dateTrtFiche") String dateTrtFiche);
	
	
	@Query(value="SELECT t from TraitementFichePFE t "
			+ "where t.traitementFichePK.fichePFEPK.conventionPK.idEt=:idET")
	List<TraitementFichePFE> getTraitementFichesByEtudiant(@Param("idET") String idET);
	
}
