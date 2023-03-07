package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.esprit.gdp.models.CodeNomenclature;
import com.esprit.gdp.models.CodeNomenclatureId;


@Repository
public interface CodeNomenclatureRepository extends JpaRepository<CodeNomenclature, CodeNomenclatureId>
{

	@Query("select cn.libNome from CodeNomenclature cn where cn.cni.codeStr=?1 and cn.cni.codeNome=?2")
	String findLibNomenclatureByCodeStrAndCodeNome(String codeStr, String codeNome);
	
	@Query("select cn.libNome from CodeNomenclature cn where cn.cni.codeStr =?1 and cn.cni.codeNome in ('11', '12')")
	List<String> findTraitementFichePFETypes(String codeStr);
	
	@Query("select cn.cni.codeNome from CodeNomenclature cn where cn.cni.codeStr=?1 and cn.libNome=?2")
	String findcodeNomeTFByCodeStrAndLibNome(String codeStr, String libNome);
	
	 
	@Query("select cn.libNome from CodeNomenclature cn where ( cn.cni.codeStr = '101' and cn.cni.codeNome=:flag )")
	String findEtatFiche(@Param("flag") String flag);
	
	@Query("select cn.libNome from CodeNomenclature cn where ( cn.cni.codeStr = '108' and cn.cni.codeNome=:flag )")
	String findEtatDepot(@Param("flag") String flag);
	
	@Query("select cn.libNome ,cn.cni.codeNome  from CodeNomenclature cn where cn.cni.codeStr = '109'")
	List<Object[]> findRendezVousSuiviTypes();
	
	@Query("select cn.libNome ,cn.cni.codeNome from CodeNomenclature cn where cn.cni.codeStr = '105'")
	List<Object[]> findVisitesTypes();
	
	@Query("select cn.libNome ,cn.cni.codeNome from CodeNomenclature cn where cn.cni.codeStr = '107' and cn.cni.codeNome != '02' ")
	List<Object[]> findEvaluationTypes();
	
	@Query("select cn.libNome ,cn.cni.codeNome from CodeNomenclature cn where cn.cni.codeStr = '110'")
	List<Object[]> findCompetencesTypes();

}
