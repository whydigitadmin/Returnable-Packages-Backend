package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.PDAttachmentVO;

public interface PDAttachmentRepo extends JpaRepository<PDAttachmentVO, Long>{

	List<PDAttachmentVO> findByRefPsId(long refPsId);

}
