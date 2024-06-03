package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.PoVO;

public interface PoRepo extends JpaRepository<PoVO, Long>{
	
    @Query(nativeQuery =true,value = "select * from po where orgid=?1")
	List<PoVO> getAllPoByOrgId(Long orgId);

    @Query(nativeQuery =true,value = "select * from po where poId=?1")
	List<PoVO> getAllPoByPoId(Long poId);

    @Query(value ="select a.pono,a.date from po a left join asset b on a.pono=b.pono where b.pono is null and a.orgid=?1",nativeQuery = true )
	Set<Object[]> getPoNoByCreateAsset(Long orgId);

}
