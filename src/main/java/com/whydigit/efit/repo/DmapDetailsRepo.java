package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.DmapDetailsVO;

public interface DmapDetailsRepo extends JpaRepository<DmapDetailsVO, Long> {

	@Query(nativeQuery = true,value = "select docidtype from dmap1 where scode=?1")
	String finddoctype(String scode);

}
