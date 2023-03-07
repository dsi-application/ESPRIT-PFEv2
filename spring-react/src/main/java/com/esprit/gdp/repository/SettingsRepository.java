package com.esprit.gdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esprit.gdp.models.Settings;
import com.esprit.gdp.models.SettingsId;


@Repository
public interface SettingsRepository extends JpaRepository<Settings, SettingsId>
{
	@Query("Select s.libelleNom from Settings s where s.abi.idPackage = '1' order by s.libelleNom desc")
	List<String> findActivatedYears();

	@Query("Select s.libelleNom from Settings s where s.abi.idPackage = '2'")
	String findGoogleAccountId();
}
