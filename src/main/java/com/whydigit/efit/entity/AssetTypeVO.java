
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
@Table(name = "assettype")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetTypeVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "assettypegen")
	@SequenceGenerator(name = "assettypegen", sequenceName = "assettypeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "assettypeid")
	private Long id;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "type")
	private String assetType;

	@Column(name = "code")
	private String typeCode;

	private boolean cancel;

	@Column(name = "createdby")
	private String createdby;

	@Column(name = "modifiedby")
	private String modifiedby;

	@Column(name = "cancelremarks")
	private String cancelremarks;

	private boolean active;
	
	private boolean eflag=false;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}