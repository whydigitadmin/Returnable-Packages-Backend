package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.TaxInvoiceKitLineVO;
import com.whydigit.efit.entity.TaxInvoiceVO;

public interface TaxInvoiceKitLineRepo extends JpaRepository<TaxInvoiceKitLineVO, Long> {

	List<TaxInvoiceKitLineVO> findByTaxInvoiceVO(TaxInvoiceVO taxInvoiceVO);

}
