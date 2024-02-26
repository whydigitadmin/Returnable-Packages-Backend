package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asset_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetGroupVO {
	@Id
	@NotEmpty(message = "Asset Code id is required")
	private String assetCodeId;
	@NotNull(message = "Organisation Id is required")
	private long orgId;
	@NotEmpty(message = "Asset Name is required")
	private String assetName;
	@NotEmpty(message = "AssetCategory is required")
	private String assetCategory;
	private boolean active;
	@NotNull(message = "Length is required")
	private float length;
	@NotNull(message = "Breath is required")
	private float breath;
	@NotNull(message = "Height is required")
	private float height;
	@NotEmpty(message = "DimUnit is required")
	private String dimUnit;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
