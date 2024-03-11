package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.IssueRequestType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issue_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequestVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long orgId;
	private long emitterId;
	private long reqAddressId;
	private Long flowTo;
	private String flowName;
	private int totalIssueItem;
	private long tat;
	private IssueRequestType irType;
	private LocalDateTime requestedDate;
	private LocalDate demandDate;
	private int issueStatus;
	private String remark;
	@OneToMany(mappedBy = "issueRequestVO", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<IssueItemVO> issueItemVO = new ArrayList<>();

}
