package com.whydigit.efit.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.CustomersBankDetailsVO;

public interface CustomersBankDetailsRepo extends JpaRepository<CustomersBankDetailsVO, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE customers_bank_details set is_default=false where id!=?1", nativeQuery = true)
	void updateDefaultAddress(Long id);

}
