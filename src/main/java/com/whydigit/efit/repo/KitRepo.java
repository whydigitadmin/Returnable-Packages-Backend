package com.whydigit.efit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.FlowVO;
import com.whydigit.efit.entity.KitVO;

@Repository
public interface KitRepo  extends JpaRepository<KitVO, Long>{

	List<KitVO> findByOrgId(Long orgId);

	@Query("select a from KitVO a where a.kitCode=?1 and a.orgId=?2")
	CharSequence findKitcode(String kitCode, long orgId);

	@Query("select a from KitVO a where a.kitCode=?1")
	Optional<KitVO> findByKitCode(String kitName);

	

}
