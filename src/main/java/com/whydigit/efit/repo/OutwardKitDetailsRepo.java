package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.OutwardKitDetailsVO;

public interface OutwardKitDetailsRepo extends JpaRepository<OutwardKitDetailsVO, Long>{

	@Query(nativeQuery = true,value = "select sum(kitqty) from outwardkitdetails where emitteroutwardid=?1")
	int getReturnQty(Long id);

}
