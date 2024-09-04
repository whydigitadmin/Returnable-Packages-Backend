package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.PaymentAdviceBillDetailsVO;
import com.whydigit.efit.entity.PaymentAdviceVO;

public interface PaymentAdviceBillDetailsRepo extends JpaRepository<PaymentAdviceBillDetailsVO, Long>{

	List<PaymentAdviceBillDetailsVO> findByPaymentAdviceVO(PaymentAdviceVO paymentAdviceVO);


}
