package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CityVO;


@Repository
public interface CityRepo extends JpaRepository<CityVO, Long> {

	boolean existsById(Long cityid);

	List<CityVO> findAllByStateAndCountryAndOrgId(String state, String country, Long orgId);

	

	boolean existsByCityNameAndCountryAndStateAndOrgId(String cityName, String country, String state, Long orgId);

	boolean existsByCityCodeAndCountryAndStateAndOrgId(String cityCode, String country, String state, Long orgId);

	boolean existsByCityNameAndCityCodeAndCountryAndStateAndOrgId(String cityName, String cityCode, String country,
			String state, Long orgId);

	boolean existsByCityNameAndCityCodeAndCountryAndStateAndOrgIdAndCityidNot(String cityName, String cityCode,
			String country, String state, Long orgId, Long cityid);

	boolean existsByCityCodeAndCountryAndStateAndOrgIdAndCityidNot(String cityCode, String country, String state,
			Long orgId, Long cityid);

	boolean existsByCityNameAndCountryAndStateAndOrgIdAndCityidNot(String cityName, String country, String state,
			Long orgId, Long cityid);


}
