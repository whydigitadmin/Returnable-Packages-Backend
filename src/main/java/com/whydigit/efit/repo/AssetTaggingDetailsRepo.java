package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.AssetTaggingDetailsVO;

public interface AssetTaggingDetailsRepo extends JpaRepository<AssetTaggingDetailsVO, Long> {

	boolean existsByRfId(String rfId);

	boolean existsByTagCode(String tagCode);

	@Query(value = "select rfid from taggingdetails", nativeQuery = true)
	String findRfIdByTagCode(String tagCode);

}
