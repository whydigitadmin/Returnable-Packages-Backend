package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.RetrievalManifestProviderVO;
@Repository
public interface RetrievalManifestProviderRepo extends JpaRepository<RetrievalManifestProviderVO, Long>{

	boolean existsByOrgIdAndTransactionNo(Long orgId, String transactionNo);

	@Query(nativeQuery = true,value = "select a.* from rim a where a.orgid=?1 and a.transactionno not in(\r\n"
			+ "select docid from binretrieval where orgid=?1 group by docid)")
	List<RetrievalManifestProviderVO> findPendingBinRetrievalDetails(Long orgId);

}
