
package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CustomersVO;


@Repository
public interface CustomersRepo extends JpaRepository<CustomersVO, Integer> {

}


