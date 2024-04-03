package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.PoVO;

public interface PoRepo extends JpaRepository<PoVO, Long>{

}
