package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.ProofOfDeliveryVO;

public interface ProofOfDeliveryRepo extends JpaRepository<ProofOfDeliveryVO, Long> {

	ProofOfDeliveryVO findByDocIdAndRfNo(String docId, String refNo);

	@Query(value = "select * from proofofdelivery where orgid=?1", nativeQuery = true)
	List<ProofOfDeliveryVO> getAllProofOfDeliveryBy(Long orgId);

}
