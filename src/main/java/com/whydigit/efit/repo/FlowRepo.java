
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
	List<FlowVO> getActiveAllFlow(Long orgId);

	@Query(value = "select a from FlowVO a Where a.orgId=?1 and a.active=true")
	List<FlowVO> findActiveByOrgId(Long orgId);

	@Query(value = "select a from FlowVO a Where a.emitterId=?1 and a.active=true")
	List<FlowVO> findActiveByEmitterId(Long emitterId);
	
	@Query(value = "select a from FlowVO a Where a.receiverId=?1 and a.active=true")
	List<FlowVO> findActiveByReceiverId(Long receiverId);

	@Query(value = "select a from FlowVO a Where a.orgId=?1 and a.emitterId=?2 and a.active=true")
	List<FlowVO> findActiveByOrgIdAndEmitterId(Long orgId, Long emitterId);
	
	@Query(value = "select a from FlowVO a Where a.orgId=?1 and a.receiverId=?2 and a.active=true")
	List<FlowVO> findActiveByOrgIdAndReceiverId(Long orgId, Long receiverId);

	@Query(value = "select a from FlowVO a Where a.id=?1  and a.active=true")
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

	@Query(value = "select a.emitter from FlowVO a where a.id=?1 ")
	String findEmiterbyFlowId(Long flow);

	@Query(value = "select a.orgin from FlowVO a where a.id=?1 ")
	String findOrigionbyFlowId(Long flow);

	@Query(nativeQuery = true, value = "select receiver,destination,orgin  from flow where flowid=?1")
	Set<Object[]> getFlowDetails(Long flowId);

//	@Query(nativeQuery = true, value = "select f.emitter,f.flow,f.receiver,f.receiverid,p.partname,p.kitno,p.partno,p.partqty from flow f, flow1 p where f.flowid=p.flowid and f.emitterid=?1 and f.orgid=?2 group by  f.emitter,f.flow,f.receiver,f.receiverid,p.partname,p.kitno,p.partno,p.partqty")
//	Set<Object[]> findKitDetailsByEmitter(Long emitterId, Long orgId);
	
	@Query(nativeQuery = true, value = "select p.kitno from flow f, flow1 p where f.flowid=p.flowid and f.emitterid=?1 and f.orgid=?2 group by  p.kitno")
	Set<Object[]> findKitDetailsByEmitter(Long emitterId, Long orgId);

	@Query(nativeQuery = true, value = "select * from flow f left join flow1 f1 on f.flowid=f1.flowid where kitno=?1")
	List<FlowVO> getFlowByKitCode(String kitcode);

	boolean existsByFlowNameAndOrgId(String flowName, long orgId);

	List<FlowVO> findByOrgId(Long orgId);

	List<FlowVO> findByEmitterId(Long emitterId);

	List<FlowVO> findByOrgIdAndEmitterId(Long orgId, Long emitterId);

	@Query(nativeQuery = true, value = "select a.emitter,a.receiver,a.flow from flow a,flow1 b where a.flowid=b.flowid and b.kitno=?1 group by a.emitter,a.receiver,a.flow;")
	Set<Object[]> findEmitterAndReceiverAndFlowByKitNo(String kitName);

	@Query(value = "select b.partname, b.partno,b.partqty \r\n" + "from flow a  join flow1 b on a.flowid = b.flowid \r\n"
			+ "where a.emitterid = b.emitterid and b.kitno=?1 and a.flowid=?2", nativeQuery = true)
	Set<Object[]> findEmitterOutwarListByKitIdAndFlowId(String kitId, Long flowId);

	@Query(nativeQuery = true, value = "SELECT a.invoiceno,a.docid,a.invoicedate FROM dispatch a WHERE a.flowid = ?1 AND NOT EXISTS ( SELECT 1 FROM oembininward b WHERE b.invoiceno = a.invoiceno)")
	Set<Object[]> getDocId(Long flowId);

	@Query(value = "select b.whlocation from  warehouse b,flow c where  b.whlocation=c.whlocation and c.flowid=?2 and b.orgid=?1 group by b.whlocation", nativeQuery = true)
	Set<Object[]> findByBranchcode(Long orgId, Long flowId);

}
