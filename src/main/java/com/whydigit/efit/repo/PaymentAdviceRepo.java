package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.PaymentAdviceVO;

public interface PaymentAdviceRepo extends JpaRepository<PaymentAdviceVO, Long> {

	List<PaymentAdviceVO> findAllByOrgId(Long orgId);

}
