package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.PaymentAdviceDetailsVO;
import com.whydigit.efit.entity.PaymentAdviceVO;

public interface PaymentAdviceDetailsRepo extends JpaRepository<PaymentAdviceDetailsVO,Long>{

	List<PaymentAdviceDetailsVO> findByPaymentAdviceVO(PaymentAdviceVO paymentAdviceVO);


}