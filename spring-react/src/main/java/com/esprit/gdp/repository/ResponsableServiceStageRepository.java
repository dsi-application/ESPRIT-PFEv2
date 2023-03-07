package com.esprit.gdp.repository;
import java.util.Optional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.RespServiceStgConfigDto;
import com.esprit.gdp.dto.ResponsableServiceStageDto;
import com.esprit.gdp.models.ResponsableServiceStage;


@Repository
public interface ResponsableServiceStageRepository extends JpaRepository<ResponsableServiceStage, String>
{
	Optional<ResponsableServiceStage> findByIdUserSce(String idUserSce);
	
	@Query("Select u.pwdJWTRSS from ResponsableServiceStage u where u.idUserSce=?1")
	String findResponsableServiceStageJWTPWDById(String idUserSce);
	
	@Query("Select u from ResponsableServiceStage u where u.idUserSce=?1")
	ResponsableServiceStage findByIdrss(String idUserSce);
	
	@Query("Select u from ResponsableServiceStage u where u.idUserSce=?1 and u.mdp=?2")
	Optional<ResponsableServiceStage> firstAuthentication(String idUserSce, String mdp);
	
	@Query("Select u.nomUserSce from ResponsableServiceStage u where u.idUserSce=?1")
	String findRespServStgFullNameById(String idUserSce);
	
	@Query("Select u.mailUserSce from ResponsableServiceStage u where u.idUserSce=?1")
	String findRespServStgMailById(String idUserSce);
	
	@Query("Select new com.esprit.gdp.dto.ResponsableServiceStageDto(u.nomUserSce, FUNCTION('to_char', u.dateModifyJwtPwd,'dd-mm-yyyy HH24:MI:SS'), u.mailUserSce, u.phoneUserSce) from ResponsableServiceStage u where u.idUserSce=?1")
	ResponsableServiceStageDto findResponsableServiceStageDtoById(String idUserSce);
	
	@Query("Select new com.esprit.gdp.dto.RespServiceStgConfigDto(u.idUserSce, u.phoneUserSce, u.mailUserSce, u.nomUserSce) "
			   + "from ResponsableServiceStage u")
	List<RespServiceStgConfigDto> findRespServiceStgConfigDtoById();
		
}
