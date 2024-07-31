package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.InvoiceProductLinesVO;
import com.whydigit.efit.entity.InvoiceVO;

public interface InvoiceProductLinesRepo extends JpaRepository<InvoiceProductLinesVO, Long> {

	List<InvoiceProductLinesVO> findByInvoiceVO(InvoiceVO invoiceVO);

}
