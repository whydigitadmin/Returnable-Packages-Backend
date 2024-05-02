package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinAllotmentVO;
import com.whydigit.efit.entity.IssueRequestVO;

public interface BinAllotmentRepo extends JpaRepository<BinAllotmentVO, Long> {

	List<BinAllotmentVO> findAll(Specification<IssueRequestVO> specification);

	@Query(nativeQuery = true, value = "select a.docid allotno,a.docdate allotdate,a.binreqno,a.binreqdate,c.address sender_address,c.city sender_city,c.state sender_state,c.gst sender_gst,d.name sender_name,e.legalname receiver_name,c.pincode from binallotment a, issuerequest b,warehouse c,\r\n"
			+ "organization d,customer e where a.orgid=d.organizationid and a.binreqno=b.docid and b.whlocationid=c.warehouseid and b.emitterid=e.customerid and a.docid=?1")
	List<Object[]> getBinAllotmentHeader(String docid);

	@Query(nativeQuery = true, value = "select a.kitcode,a.allotkitqty,b.asset product_name,b.assetcode product_code,b.skuqty*a.allotkitqty product_qty from binallotment a, binallotment1 b where \r\n"
			+ "a.binallotmentid=b.binallotmentid and a.docid=?1 \r\n"
			+ "group by a.kitcode,a.allotkitqty,b.asset,b.assetcode,b.skuqty*a.allotkitqty;")
	List<Object[]> getBinAllotmentGrid(String docid);

}
