package com.whydigit.efit.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	private int issuedQty;
	@Transient
	private int balanceQty;
	private String remark;
	private LocalDateTime reachedDate;
	@ManyToOne
	@JoinColumn(name = "issue_request_id")
	@JsonBackReference
	private IssueRequestVO issueRequestVO;
	
	public int getBalanceQty() {
		return balanceQty = this.kitQty - this.issuedQty;
	}

}
