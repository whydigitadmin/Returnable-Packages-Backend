package com.whydigit.efit.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issue_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueItemVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String kitNo;
	private int kitQty;
	private String partNo;
	private int partQty;
	private int issueItemStatus;
	private String remark;
	private LocalDateTime reachedDate;
	@ManyToOne
	@JoinColumn(name = "issue_request_id")
	private IssueRequestVO issueRequestVO;

}
