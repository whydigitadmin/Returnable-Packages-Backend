package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.BinAllotmentVO;
import com.whydigit.efit.entity.IssueRequestVO;

public interface BinAllotmentRepo extends JpaRepository<BinAllotmentVO, Long> {

	List<BinAllotmentVO> findAll(Specification<IssueRequestVO> specification);

	

}
