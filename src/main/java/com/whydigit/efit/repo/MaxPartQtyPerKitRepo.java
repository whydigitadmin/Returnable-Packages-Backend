package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.MaxPartQtyPerKitVO;

public interface MaxPartQtyPerKitRepo extends JpaRepository<MaxPartQtyPerKitVO, Long>{
	
@Query(value = "select * from vw_max_partqty_perkit where org_id=?1",nativeQuery = true)
	List<MaxPartQtyPerKitVO> findMaxPartQtyPerKitByOrgId(Long orgId);

}
