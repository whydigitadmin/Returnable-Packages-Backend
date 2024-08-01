package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.TaxInvoiceProductLineVO;
import com.whydigit.efit.entity.TaxInvoiceVO;

public interface TaxInvoiceProductLineRepo extends JpaRepository<TaxInvoiceProductLineVO, Long> {

	List<TaxInvoiceProductLineVO> findByTaxInvoiceVO(TaxInvoiceVO taxInvoiceVO);

}
