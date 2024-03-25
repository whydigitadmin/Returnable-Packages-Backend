package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="stockbranch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockBranchVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stockbranchgen")
	@SequenceGenerator(name = "stockbranchgen", sequenceName = "stockbranchseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockbranchid")
	private Long id;
	
	@Column(name="cancel")
	private boolean cancel;
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="createdby", length = 50)
	private String createdBy;
	
	@Column(name="modifiedby", length = 50)
	private String modifiedBy;
	
	@Column(name="cancelremarks", length = 50)
	private String cancelRemarks;
	
	@Column(name="branchcode", length = 10)
	private String branchCode;
	
	@Column(name="branch", length = 50)
	private String branch;
	
	@Column(name="orgid")
	private Long orgId;
	
	
	@Embedded
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();
	
	
	
	
	
	
	
	
	

}
