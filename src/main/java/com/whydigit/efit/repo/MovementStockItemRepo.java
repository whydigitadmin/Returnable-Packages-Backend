package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.MovementStockItemVO;

public interface MovementStockItemRepo extends JpaRepository<MovementStockItemVO, Long>{

}
