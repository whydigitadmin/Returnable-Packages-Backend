package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.CityVO;


@Repository
public interface CityRepo extends JpaRepository<CityVO, Integer> {

}
