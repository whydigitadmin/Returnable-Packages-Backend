
package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.ManufacturerVO;


@Repository
public interface ManufacturerRepo extends JpaRepository<ManufacturerVO, Integer> {
}
