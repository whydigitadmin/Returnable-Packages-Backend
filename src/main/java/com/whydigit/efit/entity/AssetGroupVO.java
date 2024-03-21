package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assetgroup")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetGroupVO {
	
	@Id
	@NotEmpty(message = "Asset Code id is required")
	@Column(name="assetcode",length = 25)
	private String assetCodeId;
	
	@NotNull(message = "Organisation Id is required")
	@Column(name="orgid",length = 25)
	private Long orgId;
	
	@NotEmpty(message = "Asset Name is required")
	@Column(name="asset",length = 25)
	private String assetName;
	
	@NotEmpty(message = "AssetCategory is required")
	@Column(name="category",length = 25)
	private String assetCategory;
	
	private boolean active;
	
	@NotNull(message = "Length is required")
	@Column(name="length",length = 5)
	private float length;
	
	@NotNull(message = "Breath is required")
	@Column(name="breath",length = 5)
	private float breath;
	
	@NotNull(message = "Height is required")
	@Column(name="height",length = 5)
	private float height;
	
	@NotEmpty(message = "DimUnit is required")
	@Column(name="dimunit",length = 25)
	private String dimUnit;
	
	private boolean cancel;
	
	@Column(name="createdby",length = 25)
	private String createdby;
	
	@Column(name="modifiedby",length = 25)
	private String modifiedby;
	
	@Column(name="cancelremarks",length = 50)
	private String cancelremarks;
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
