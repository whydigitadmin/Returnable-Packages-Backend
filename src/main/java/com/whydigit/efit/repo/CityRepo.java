package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CityVO;


@Repository
public interface CityRepo extends JpaRepository<CityVO, Long> {

	boolean existsById(Long cityid);

	@Query(nativeQuery = true,value = "select * from city where orgid=?1 and country=?2 and state=?3 and active=1")
	List<CityVO> findAllByStateAndCountryAndOrgId(Long orgId, String country, String state);

	

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

	boolean existsByCityNameAndCityCodeAndStateAndCountryAndOrgId(String city, String code, String state,
			String country, Long orgId);

	boolean existsByCityNameAndCountryAndOrgId(String city, String country, Long orgId);

	boolean existsByStateAndCountryAndOrgId(String state, String country, Long orgId);

	boolean existsByCityCodeAndCountryAndOrgId(String code, String country, Long orgId);
	
	@Query(nativeQuery = true,value = "select * from city where orgid=?1 and active=1")
	List<CityVO> findAllActiveCity(Long orgId);

	List<CityVO> findAllByOrgId(Long orgId);


}
