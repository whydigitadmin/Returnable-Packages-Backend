package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.RetrievalManifestProviderDetailsVO;
import com.whydigit.efit.entity.RetrievalManifestProviderVO;
@Repository
public interface RetrievalManifestProviderDetailsRepo extends JpaRepository<RetrievalManifestProviderDetailsVO, Long>{

	List<RetrievalManifestProviderDetailsVO> findByRetrievalManifestProviderVO(
			RetrievalManifestProviderVO retrievalManifestProviderVO);

}
