
package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetCategoryVO;


@Repository
public interface AssetCategoryRepo extends JpaRepository<AssetCategoryVO, Integer> {
}
