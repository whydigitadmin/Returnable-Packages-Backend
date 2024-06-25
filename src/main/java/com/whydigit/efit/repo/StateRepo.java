package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.StateVO;

@Repository
public interface StateRepo extends JpaRepository<StateVO, Long> {

	List<StateVO> findAllStateByCountryAndOrgId (String country, Long orgId);

	boolean existsByStateNameAndStateCodeAndCountryAndOrgId(String stateName, String stateCode, String country,
			Long orgId);

	boolean existsByStateNameAndCountryAndOrgId(String stateName, String country, Long orgId);

	boolean existsByStateCodeAndCountryAndOrgId(String stateCode, String country, Long orgId);

	boolean existsByStateNameAndStateCodeAndCountryAndOrgIdAndIdNot(String stateName, String stateCode, String country,
			Long orgId, Long id);

	boolean existsByStateNameAndCountryAndOrgIdAndIdNot(String stateName, String country, Long orgId, Long id);

	boolean existsByStateCodeAndCountryAndOrgIdAndIdNot(String stateCode, String country, Long orgId, Long id);

	boolean existsByStateNameAndStateCodeAndStateNoAndCountryAndOrgId(String state, String code, String country,
			String no, Long orgId);


	

}
