package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.IssueManifestProviderVO;
@Repository
public interface IssueManifestProviderRepo extends JpaRepository<IssueManifestProviderVO, Long>{

	boolean existsByOrgIdAndTransactionNo(Long orgId, String transactionNo);

	@Query(value = "select a from IssueManifestProviderVO a where a.orgId=?1 and a.transactionNo not in(select b.docId from IssueRequestVO b where b.orgId=?1)")
	List<IssueManifestProviderVO> findAllIssueManifeasrProvider(Long orgId);


}
