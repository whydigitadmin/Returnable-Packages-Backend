package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
import com.whydigit.efit.entity.PodVO; 

public interface PodRepo extends JpaRepository<PodVO, Long> {

	@Query(nativeQuery =true,value ="select * from pod where podid=?1")
	List<PodVO> getAllPoByPoId(Long podId);

	@Query(nativeQuery =true,value ="select * from pod where orgid=?1")
	List<PodVO> getAllPodByPoId(Long orgId);

}
