package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.whydigit.efit.entity.KitAssetVO;

@Repository
public interface KitAssetRepo  extends JpaRepository<KitAssetVO,Long>{

}
