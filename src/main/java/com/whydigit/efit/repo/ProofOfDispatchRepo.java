package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.ProofOfDeliveryVO;

public interface ProofOfDispatchRepo extends JpaRepository<ProofOfDeliveryVO, Long>{

}
