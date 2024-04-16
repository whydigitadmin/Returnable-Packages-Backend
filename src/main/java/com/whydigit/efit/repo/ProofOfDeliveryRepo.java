package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.ProofOfDeliveryVO;

public interface ProofOfDeliveryRepo extends JpaRepository<ProofOfDeliveryVO, Long>{

	ProofOfDeliveryVO findByDocIdAndRfNo(String docId, String refNo);

}
