package com.whydigit.efit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.DeclarationAndNotesVO;
@Repository
public interface DeclarationAndNotesRepo extends JpaRepository<DeclarationAndNotesVO, Long> {

}
