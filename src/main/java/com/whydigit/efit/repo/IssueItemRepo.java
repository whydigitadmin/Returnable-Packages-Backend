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
	@Query(value = "update issuerequest2 itm set itm.issueitemstatus=?2 where itm.issuerequest2id=?1", nativeQuery = true)
	int cancelIssueRequestByIssueRequestId(Long issueRequestId, int issueRequestItemStatusCancelled);

	@Modifying
	@Transactional
	@Query(value = "update issuerequest2 a set a.kitqty=?2 where a.issuerequest2id=?1", nativeQuery = true)
	int loadKitQty(Long irItemId, Long kitQty);

	@Modifying
	@Transactional
	@Query(value = "update issuerequest2 a set a.approvedstatus=1 where issuerequest2id=?1", nativeQuery = true)
	void updateApptovedStatus(Long issueRequestId);

}
