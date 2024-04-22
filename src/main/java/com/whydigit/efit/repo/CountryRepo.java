package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CountryVO;

@Repository
public interface CountryRepo extends JpaRepository<CountryVO, Long> {

	List<CountryVO> findAllByOrgId(Long orgId);

	boolean existsByCountryAndOrgId(String country, Long orgId);

	boolean existsByCountryCodeAndOrgId(String countryCode, Long orgId);

	boolean existsByCountryAndCountryCodeAndOrgId(String country, String countryCode, Long orgId);

}
