package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.InvoiceProductLinesVO;
import com.whydigit.efit.entity.InvoiceVO;

public interface InvoiceRepo extends JpaRepository<InvoiceVO, Long>{

	@Query(value = "select a from InvoiceVO a where a.orgId=?1")
	List<InvoiceVO> findAllByOrgId(Long orgId);
	


}
