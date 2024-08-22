package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.IssueManifestProviderDetailsVO;
import com.whydigit.efit.entity.IssueManifestProviderVO;
@Repository
public interface IssueManifestProviderDetailsRepo extends JpaRepository<IssueManifestProviderDetailsVO, Long>{

	List<IssueManifestProviderDetailsVO> findByIssueManifestProviderVO(IssueManifestProviderVO issueManifestProviderVO);

}
