package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.FlowVO;

@Repository
public interface FlowDetailRepo extends JpaRepository<FlowVO, Long> {

}


