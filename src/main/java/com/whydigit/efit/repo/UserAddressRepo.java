package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.efit.entity.UserAddressVO;

public interface UserAddressRepo  extends JpaRepository<UserAddressVO, Long>{

}
