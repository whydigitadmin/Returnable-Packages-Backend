package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.PoVO;

public interface PoRepo extends JpaRepository<PoVO, Long>{
	
    @Query(nativeQuery =true,value = "select * from po where orgid=?1")
	List<PoVO> getAllPoByOrgId(Long orgId);

    @Query(nativeQuery =true,value = "select * from po where poId=?1")
	List<PoVO> getAllPoByPoId(Long poId);

}
