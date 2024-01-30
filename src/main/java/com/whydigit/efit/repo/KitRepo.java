package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.KitVO;

@Repository
public interface KitRepo  extends JpaRepository<KitVO, String>{

}
