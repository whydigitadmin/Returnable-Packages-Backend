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
@Table(name = "service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "servicegen")
	@SequenceGenerator(name = "servicegen", sequenceName = "serviceseqge", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "serviceid")
	private Long id;
	private boolean cancel;
	private boolean active;
	@Column(name="createdby",length=25)
	private String createdBy;
	@Column(name="modifiedby",length=25)
	private String modifiedBy;
	@Column(name="cancelremarks",length=25)
	private String cancelRemarks;
	@Column(name="code",length=2)
	private String code;
	@Column(name="description",length=25)
	private String description;
	
	@Column(name="orgid")
	private Long orgId;
	
	
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	
	
	
	
	
	
	
	
}
