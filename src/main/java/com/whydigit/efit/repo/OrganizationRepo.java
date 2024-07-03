package com.whydigit.efit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.OrganizationVO;

public interface OrganizationRepo extends JpaRepository<OrganizationVO, Long>{

	boolean existsByName(String name);

	boolean existsByCode(String code);

	boolean existsByEmail(String email);

	@Query(value = "select * from organization where organizationid=?1",nativeQuery = true)
	Optional<OrganizationVO> findCompanyById(Long id);

}
