package com.whydigit.efit.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.efit.entity.IssueItemVO;

public interface IssueItemRepo extends JpaRepository<IssueItemVO, Long> {
	@Modifying
	@Transactional
	@Query(value = "update IssueItemVO itm set itm.issueItemStatus=?2 where itm.id=?1")
	int cancelIssueRequestByIssueRequestItemId(Long issueRequestItemId, int issueRequestStatusCancelled);

	@Modifying
	@Transactional
	@Query(value = "update issue_item itm set itm.issue_item_status=?2 where itm.issue_request_id=?1", nativeQuery = true)
	int cancelIssueRequestByIssueRequestId(Long issueRequestId, int issueRequestItemStatusCancelled);

	@Modifying
	@Transactional
	@Query(value = "update issue_item a set a.kit_qty=?2 where a.id=?1", nativeQuery = true)
	int loadKitQty(Long irItemId, Long kitQty);

	@Modifying
	@Transactional
	@Query(value = "update issue_item a set a.approved_status=1 where issue_request_id=?1", nativeQuery = true)
	void updateApptovedStatus(Long issueRequestId);

}
