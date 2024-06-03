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
@Table(name="returnstock")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReturnStockVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "returnstockgen")
	@SequenceGenerator(name = "returnstockgen", sequenceName = "returnstockseq", initialValue = 1000000001, allocationSize = 1)
	private Long returnstockid;
	
	@Column(name="issueitemid")
	private Long issue_item_id;
		
	@Column(name="qty")
	private int qty;
	
	
	@Column(name = "createdby", length = 25)
	private String createdBy;
	
	@Column(name = "modifiedy", length = 25)
	private String modifiedBy;
	
	@Column(name = "cancelremarks", length = 25)
	private String cancelRemark;
	
	
	private boolean cancel;
	
	
	@Embedded
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();
	

}
