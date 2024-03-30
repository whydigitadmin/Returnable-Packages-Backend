package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issuerequest2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueItemVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "issuerequest2gen")
	@SequenceGenerator(name = "issuerequest2gen", sequenceName = "issuerequest2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "issuerequest2id")
	private long id;
	@Column(name = "issuerequest2rowid", length = 15)
	private int issuerequest2rowid;
	@Column(name = "kitcode", length = 25)
	private String kitName;
	@Column(name = "kitqty", length = 15)
	private int kitQty;
	@Column(name = "partno", length = 25)
	private String partNo;
	@Column(name = "partname", length = 255)
	private String partName;
	@Column(name = "partqty", length = 15)
	private int partQty;
	@Column(name = "issueitemstatus", length = 15)
	private int issueItemStatus;
	@Column(name = "issuedqty", length = 15)
	private int issuedQty;
	@Transient
	private int balanceQty;
	@Column(name = "remark")
	private String remark;
	@Column(name = "reacheddate")
	private String reachedDate;
	@Column(name="approvedstatus")
	private boolean approvedStatus;
	@ManyToOne
	@JoinColumn(name = "issuerequestid")
	@JsonBackReference
	private IssueRequestVO issueRequestVO;

	public int getBalanceQty() {
		return balanceQty = this.kitQty - this.issuedQty;
	}

	@JsonManagedReference
	@OneToOne(mappedBy = "issueItemVO", cascade = CascadeType.ALL)
	private InwardVO inwardVO;

	@JsonManagedReference
	@OneToOne(mappedBy = "issueItemVO", cascade = CascadeType.ALL)
	private EmitterOutwardVO outwardVO;

	@JsonManagedReference
	@OneToMany(mappedBy = "issueItemVO", cascade = CascadeType.ALL)
	private List<IssueRequestApprovedVO> issueRequestApprovedVO;

}
