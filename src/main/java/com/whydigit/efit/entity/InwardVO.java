package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issuerequest3")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "issuerequest3gen")
	@SequenceGenerator(name = "issuerequest3gen", sequenceName = "issuerequest3seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name ="issuerequest3id")
	private long id;
	private String status;
	@Column(name ="returnqty",length =15)
	private int returnQty;
	@Column(name ="netqtyrecieved",length =15)
	private int netQtyRecieved;
	private boolean netRecAcceptStatus;
	@Column(name ="issuerequest2rowid",length=15)
	private int issueRequest2RowId;		
	@Column(name ="remarks",length =200)
	private String remarks;
	
	@JsonBackReference
	@OneToOne
    @JoinColumn(name = "issueitemid")
	private IssueItemVO issueItemVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
