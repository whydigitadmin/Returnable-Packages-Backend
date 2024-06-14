package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.GathereingEmptyDetailsVO;
@Repository
public interface GatheringEmptyDetailsRepo extends JpaRepository<GathereingEmptyDetailsVO, Long>{

}
