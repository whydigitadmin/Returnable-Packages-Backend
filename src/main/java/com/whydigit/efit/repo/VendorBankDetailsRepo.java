package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.VendorBankDetailsVO;

@Repository
public interface VendorBankDetailsRepo extends JpaRepository<VendorBankDetailsVO, Long>{

}
