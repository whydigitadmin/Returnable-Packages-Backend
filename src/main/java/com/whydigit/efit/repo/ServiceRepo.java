package com.whydigit.efit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.ServiceVO;

public interface ServiceRepo extends JpaRepository<ServiceVO, Long>{

	Optional<ServiceVO> findById(String code);

}
