
package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.ManufacturerProductVO;


@Repository
public interface ManufacturerProductRepo extends JpaRepository<ManufacturerProductVO, Integer> {
}
