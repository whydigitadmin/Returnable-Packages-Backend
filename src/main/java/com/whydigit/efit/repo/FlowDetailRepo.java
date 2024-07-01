package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.FlowDetailVO;
import com.whydigit.efit.entity.FlowVO;

@Repository
public interface FlowDetailRepo extends JpaRepository<FlowDetailVO, Long> {

	List<FlowDetailVO> findByFlowVO(FlowVO flowVO);

}


