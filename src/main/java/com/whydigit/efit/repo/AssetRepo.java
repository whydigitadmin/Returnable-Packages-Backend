
package com.whydigit.efit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.AssetVO;


@Repository
public interface AssetRepo extends JpaRepository<AssetVO, Integer> {


}
