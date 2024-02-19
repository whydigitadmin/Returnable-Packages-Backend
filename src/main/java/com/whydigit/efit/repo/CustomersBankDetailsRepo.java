package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.CustomersBankDetailsVO;

public interface CustomersBankDetailsRepo  extends JpaRepository<CustomersBankDetailsVO, Long>{

}
