package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CountryVO;

@Repository
public interface CountryRepo extends JpaRepository<CountryVO, Long> {

	@Query(value = "select * from country where orgid=?1 and active=true",nativeQuery =true)
	List<CountryVO> findAllByOrgId(Long orgId);

	boolean existsByCountryAndOrgId(String country, Long orgId);

	boolean existsByCountryCodeAndOrgId(String countryCode, Long orgId);

	boolean existsByCountryAndCountryCodeAndOrgId(String country, String countryCode, Long orgId);

	boolean existsByCountryAndCountryCodeAndOrgIdAndIdNot(String country, String countryCode, Long orgId, Long id);

	boolean existsByCountryAndOrgIdAndIdNot(String country, Long orgId, Long id);

	boolean existsByCountryCodeAndOrgIdAndIdNot(String countryCode, Long orgId, Long id);
	
    @Query(value = "select * from country where orgid=?1",nativeQuery = true)
	List<CountryVO> findAllCountry(Long orgId);


}
