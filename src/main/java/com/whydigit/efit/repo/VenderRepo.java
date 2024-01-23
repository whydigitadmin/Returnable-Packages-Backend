
package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.VenderVO;

@Repository
public interface VenderRepo extends JpaRepository<VenderVO, Integer> {

}
