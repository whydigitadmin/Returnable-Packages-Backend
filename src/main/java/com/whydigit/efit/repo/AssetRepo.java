
package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetVO;


@Repository
public interface AssetRepo extends JpaRepository<AssetVO, Integer> {


}
