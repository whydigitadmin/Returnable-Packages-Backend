
package com.whydigit.efit.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.efit.entity.FlowVO;

@Repository
public interface FlowRepo extends JpaRepository<FlowVO, Long> {

	@Query(value = "select a from FlowVO a Where a.orgId=?1")
	List<FlowVO> getAllFlow(Long orgId);

	List<FlowVO> findByOrgId(Long orgId);

	List<FlowVO> findByEmitterId(Long emitterId);

	List<FlowVO> findByOrgIdAndEmitterId(Long orgId, Long emitterId);

	List<FlowVO> findById(String flowId);

	@Query(value = "select a.flowid,a.emitter,a.flow from flow a where a.orgid=?1 and a.emitterid=?2 group by a.flowid,a.emitter,a.flow", nativeQuery = true)
	Set<Object[]> getFlowNameByOrgID(Long orgId, Long emitterId);

	@Query(nativeQuery = true, value = "select a.displayname from customer a where customerid=?1")
	String findEmiterbyId(long emitterId);

	@Query(nativeQuery = true, value = "select a.emitterid,a.emitter from flow a where a.orgid=?1 and a.warehouseid=?2 group by a.emitterid,a.emitter")
	Set<Object[]> findEmitterByWarehouseId(Long orgId, Long warehouseId);

	@Query(value = "select a.whlocation from warehouse a where a.warehouseid=?1", nativeQuery = true)
	String getWarehouseLocationByLocationId(Long warehouseId);

	@Query(value = "select a.displayname from customer a where a.customerid=?1", nativeQuery = true)
	String getReceiverByReceiverId(Long receiverId);

	@Query(nativeQuery = true,value = "select a.emitter from FlowVO a where a.id=?1 ")
	String findEmiterbyFlowId(String flow);

	@Query(nativeQuery = true,value = "select a.orgin from FlowVO a where a.id=?1 ")
	String findOrigionbyFlowId(String flow);

}
