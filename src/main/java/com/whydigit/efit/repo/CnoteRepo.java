package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.CnoteVO;

public interface CnoteRepo extends JpaRepository<CnoteVO, Long> {

}
