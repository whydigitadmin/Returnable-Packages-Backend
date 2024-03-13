package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.MovementStockVO;

public interface MovementStockRepo extends JpaRepository<MovementStockVO, Long>{

}
