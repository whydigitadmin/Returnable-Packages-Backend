package com.whydigit.efit.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issue_request_approved")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequestApprovedVO {
	@Id
	private String irApprovedId;
	private Long approvedId;
	private String approverName;
	private LocalDateTime approvedDate;
	private int quantity;
	@ManyToOne
	@JoinColumn(name = "issue_item_id")
	@JsonBackReference
	private IssueItemVO issueItemVO;

}
