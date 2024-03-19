package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.CustomerAttachmentVO;

public interface CustomerAttachmentRepo extends JpaRepository<CustomerAttachmentVO, Long> {

	List<CustomerAttachmentVO> findByCustomerId(Long id);

}
