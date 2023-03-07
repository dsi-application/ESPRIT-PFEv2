package com.esprit.gdp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.dto.ChefCoordConfigDto;
import com.esprit.gdp.dto.PedagogicalCoordinatorDto;
import com.esprit.gdp.models.PedagogicalCoordinator;
import com.esprit.gdp.models.ResponsableStageId;


@Repository
public interface PedagogicalCoordinatorRepository extends JpaRepository<PedagogicalCoordinator, ResponsableStageId>
{
	
	PedagogicalCoordinator findByToken(String token);

	@Query("Select u from PedagogicalCoordinator u where u.pedagogicalCoordinator.idEns =?1")
	PedagogicalCoordinator findPedagogicalCoordinatorStage(String idEns);
	
	@Query("Select u.pedagogicalCoordinator.idEns from PedagogicalCoordinator u where "
			+ "u.option.id.codeOption =?1 and "
			+ "u.login is not null and "
			+ "u.login like 'CPS%'")
	String findPedagogicalCoordinatorByCodeOption(String codeOption);
	
	@Query("Select u.login from PedagogicalCoordinator u where "
			+ "u.option.id.codeOption =?1 and "
			+ "u.login is not null and "
			+ "u.login like 'CPS%'")
	String findPedagogicalCoordinatorMailByCodeOption(String codeOption);
	
	@Query("Select SUBSTRING(u.login, 5, 3) from PedagogicalCoordinator u where u.login = 'CPS_SIM@esprit.tn'")
	String lol();
	
	@Query("Select u from PedagogicalCoordinator u where u.pedagogicalCoordinator.idEns =?1")
	List<PedagogicalCoordinator> findPedagogicalCoordinator(String idEns);
	
	@Query("Select u from PedagogicalCoordinator u where u.login =?1")
	Optional<PedagogicalCoordinator> findPedagogicalCoordinatorByLogin(String login);
	
	@Query("Select u.pwdJWTPC from PedagogicalCoordinator u where u.login=?1")
	String findPedagogicalCoordinatorJWTPWDById(String idUserSce);
	
	@Query("Select u.option.id.codeOption from PedagogicalCoordinator u where u.login=?1")
	String findPedagogicalCoordinatorCodeOptionById(String idUserSce);
	
	@Query("Select u from PedagogicalCoordinator u where u.login=?1 and u.mdp=?2")
	Optional<PedagogicalCoordinator> firstAuthentication(String login, String mdp);
	
//	@Query("Select new com.esprit.gdp.dto.PedagogicalCoordinatorDto(u.fullName, u.phoneNumber, u.dateModifyJwtPwd, select u1.id.codeOption from PedagogicalCoordinator u1 where u1.login=?1) "
//			+ "from PedagogicalCoordinator u where u.login=?1")
//	List<PedagogicalCoordinatorDto> findPedagogicalCoordinatorDtoByPCMailLISTA(String login);
	
	@Query("Select new com.esprit.gdp.dto.PedagogicalCoordinatorDto(u.phoneNumber, FUNCTION('to_char', u.dateModifyJwtPwd,'dd-mm-yyyy HH24:MI:SS'), u.option.id.codeOption) "
			+ "from PedagogicalCoordinator u where u.login=?1")
	PedagogicalCoordinatorDto findPedagogicalCoordinatorDtoByPedaCoordMail(String login);
	
	@Query("Select u.fullName from PedagogicalCoordinator u where u.login =?1")
	String findPedagogicalCoordinatorFullNameByMail(String pcMail);
	
	@Query("Select u.option.libOption from PedagogicalCoordinator u where u.pedagogicalCoordinator.idEns =?1")
	List<String> listOptionsByPedagogicalCoordinator(String idPC);
	
	@Query("Select lower(u.option.libOption) from PedagogicalCoordinator u where u.pedagogicalCoordinator.idEns =?1")
	List<String> listLowerOptionsByPedagogicalCoordinator(String idPC);
	
	@Query("Select u.pedagogicalCoordinator.idEns from PedagogicalCoordinator u where u.login =?1")
	List<String> gotIdPedagogicalCoordinator(String pcMail);
	
	@Query("Select u.login from PedagogicalCoordinator u where lower(u.option.id.codeOption) =?1 and u.login like '%CPS%'")
	String gotMailPedagogicalCoordinatorByOption(String codeOption);
	
	@Query("Select u.teacher.idEns from PedagogicalCoordinator u where lower(u.option.id.codeOption) =?1 and u.login like '%CPS%'")
	// @Query("Select u.teacher.idEns from PedagogicalCoordinator u where lower(u.option.id.codeOption) =?1 and (u.login like '%CPS%' or u.login is null)")
	String gotIdPedagogicalCoordinatorByOption(String codeOption);

	@Query("Select u.teacher.idEns from PedagogicalCoordinator u where lower(u.option.id.codeOption) =?1")
	List<String> gotIdPedagogicalCoordinatorByOptionNew(String codeOption);
	
	@Query("Select u.teacher.idEns from PedagogicalCoordinator u where u.pedagogicalCoordinator.idEns =?1 and u.login like '%CPS%'")
	String gotIdPedagogicalCoordinatorByIdEns(String idCPS);
	
	@Query("Select u.login from PedagogicalCoordinator u where u.pedagogicalCoordinator.idEns =?1")
	List<String> gotAllMAilsPedagogicalCoordinatorById(String codeOption);
	
	@Query("Select u from PedagogicalCoordinator u where lower(u.option.id.codeOption) =?1 and (u.login like '%CPS%' or u.login is null)")
	List<PedagogicalCoordinator> gotAllIdsPedagogicalCoordinatorByOption(String codeOption);
	
	@Query("Select u.login from PedagogicalCoordinator u where u.pedagogicalCoordinator.idEns =?1 and u.login like '%CPS%'")
	String gotMailPedagogicalCoordinatorByIdEns(String idCPS);
	
	@Query("Select u.teacher.idEns from PedagogicalCoordinator u where lower(u.option.id.codeOption) =?1 and u.privilege = 'CPS'")
	String gotIdEnsPedagogicalCoordinatorByOption(String codeOption);
	
	@Query("Select u.login from PedagogicalCoordinator u where u.pedagogicalCoordinator.idEns =?1 and u.login like '%CPS%'")
	String gotAllMAilsPedagogicalCoordinatorByIdNew(String codeOption);
	
	@Query("Select new com.esprit.gdp.dto.ChefCoordConfigDto(u.login, u.phoneNumber, u.fullName, u.privilege) "
		  +"from PedagogicalCoordinator u where u.login is not null order by u.login")
	List<ChefCoordConfigDto> findChefCoordConfigDto();
		
}
