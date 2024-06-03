package com.whydigit.efit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.AssetTaggingDetailsVO;

public interface AssetTaggingDetailsRepo extends JpaRepository<AssetTaggingDetailsVO, Long> {

	boolean existsByRfId(String rfId);

	boolean existsByTagCode(String tagCode);

	Optional<AssetTaggingDetailsVO> findByRfId(String rfId);

	Optional<AssetTaggingDetailsVO> findByTagCode(String tagCode);

	@Query(value = "select rfid from taggingdetails where tagcode=?1", nativeQuery = true)
	String findRfIdByTagCode(String tagCode);

}
