package com.whydigit.efit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.CustomersAddressVO;

public interface CustomersAddressRepo extends JpaRepository<CustomersAddressVO, Long>{
@Query(value="select ca.* from customers_address ca,customer c where ca.customers_id=c.id and c.id=?1",nativeQuery = true)
	List<CustomersAddressVO> getCustomerAddressByCustomerId(Long customerId);
																																																																																																																																																																																																																											 


}

																																																																																																																																																																																																																																					

