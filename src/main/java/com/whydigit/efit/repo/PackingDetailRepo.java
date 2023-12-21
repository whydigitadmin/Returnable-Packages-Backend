
package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.PackingDetailVO;


@Repository
public interface PackingDetailRepo extends JpaRepository<PackingDetailVO, Integer> {
}
