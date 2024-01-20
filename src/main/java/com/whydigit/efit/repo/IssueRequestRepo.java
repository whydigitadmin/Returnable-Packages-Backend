package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.IssueRequestVO;

public interface IssueRequestRepo extends JpaRepository<IssueRequestVO, Long>{

}
