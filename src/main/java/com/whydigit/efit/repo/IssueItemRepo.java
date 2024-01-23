package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.IssueItemVO;

public interface IssueItemRepo extends JpaRepository<IssueItemVO, Long>{

}
