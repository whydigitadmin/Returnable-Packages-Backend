
package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetGroupVO;


@Repository
public interface AssetGroupRepo extends JpaRepository<AssetGroupVO, String> {
}
