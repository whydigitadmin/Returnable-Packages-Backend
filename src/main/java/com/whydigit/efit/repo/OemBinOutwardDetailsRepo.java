package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.whydigit.efit.entity.OemBinOutwardDetailsVO;

@Service
public interface OemBinOutwardDetailsRepo extends JpaRepository<OemBinOutwardDetailsVO, Long> {

}
