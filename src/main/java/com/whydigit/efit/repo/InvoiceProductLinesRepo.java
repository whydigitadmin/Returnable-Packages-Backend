package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.InvoiceProductLinesVO;
import com.whydigit.efit.entity.InvoiceVO;

public interface InvoiceProductLinesRepo extends JpaRepository<InvoiceProductLinesVO, Long> {

//	@Query(value="select a from InvoiceProductLinesVO a where a.invoiceVO=?1")
	List<InvoiceProductLinesVO> findByInvoiceVO(InvoiceVO invoiceVO);

}
