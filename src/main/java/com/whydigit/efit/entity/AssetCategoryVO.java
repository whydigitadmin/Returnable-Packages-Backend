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
@Table(name = "assetcategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetCategoryVO {
	
    @Id	
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "assetcategorygen")
	@SequenceGenerator(name = "assetcategorygen", sequenceName = "assetcategoryseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name="assetcategoryid")
    private Long id;
    
	@Column(name="categorycode")
	private String categoryCode;
	
	@NotNull(message = "Organisation Id is required")
	@Column(name="orgid")
	private Long orgId;
	
	@NotEmpty(message = "Asset Category is required")
	@Column(name="category")
	private String category;
	
	@NotEmpty(message = "AssetType is required")
	@Column(name="assettype")
	private String assetType;
	
	private boolean active;
	
	@NotNull(message = "Length is required")
	@Column(name="length")
	private float length;
	
	@NotNull(message = "Breath is required")
	@Column(name="breath")
	private float breath;
	
	@NotNull(message = "Height is required")
	@Column(name="height")
	private float height;
	
	@NotEmpty(message = "DimUnit is required")
	@Column(name="dimunit")
	private String dimUnit;
	
	private boolean cancel;
	
	@Column(name="createdby")
	private String createdby;
	
	@Column(name="modifiedby")
	private String modifiedby;
	
	@Column(name="cancelremarks")
	private String cancelremarks;
	
	@Builder.Default
	@Column(name="eflag")
	private boolean eflag=false;
	
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
