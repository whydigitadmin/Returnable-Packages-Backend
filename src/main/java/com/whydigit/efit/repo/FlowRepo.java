
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

	@Query(value = "select a.id,a.emitter,a.flow_name from flow a where a.org_id=?1 and a.emitter_id=?2 group by a.id,a.emitter,a.flow_name", nativeQuery = true)
	Set<Object[]> getFlowNameByOrgID(Long orgId, Long emitterId);

	@Query(nativeQuery = true, value = "select a.display_name from customer a where id=?1")
	String findEmiterbyId(long emitterId);

	@Query(nativeQuery = true, value = "select a.emitter_id,a.emitter from flow a where a.org_id=?1 and a.warehouse_id=?2 group by a.emitter_id,a.emitter")
	Set<Object[]> findEmitterByWarehouseId(Long orgId, Long warehouseId);

	@Query(value = "select a.warehouse_location from warehouse a where a.warehosue_id=?1", nativeQuery = true)
	String getWarehouseLocationByLocationId(Long warehouseId);

	@Query(value = "select a.display_name from customer a where a.id=?1", nativeQuery = true)
	String getReceiverByReceiverId(Long receiverId);

}
