package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.TaxInvoiceVO;

public interface TaxInvoiceRepo extends JpaRepository<TaxInvoiceVO, Long> {

	List<TaxInvoiceVO> findAllByOrgId(Long orgId);

}
