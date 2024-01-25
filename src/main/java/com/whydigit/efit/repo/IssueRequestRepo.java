package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.whydigit.efit.entity.IssueRequestVO;

public interface IssueRequestRepo extends  JpaRepository<IssueRequestVO, Long>,JpaSpecificationExecutor<IssueRequestVO>{
	
    List<IssueRequestVO> findAll(Specification<IssueRequestVO> specification);

}
