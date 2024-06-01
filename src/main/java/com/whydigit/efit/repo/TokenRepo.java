package com.whydigit.efit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.TokenVO;

@Repository
public interface TokenRepo extends JpaRepository<TokenVO, String>{

	@Query(nativeQuery = true, value="SELECT * FROM token WHERE user_id = ?1 ORDER BY created_date DESC LIMIT 1")
    Optional<TokenVO> findLatestTokenByUserId(Long userId);

	
}
