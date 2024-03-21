
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
@Table(name = "assetcategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "assetcategorygen")
	@SequenceGenerator(name = "assetcategorygen", sequenceName = "assetcategoryseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="assetcategoryid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	@Column(name="category",length = 25)
	private String assetCategory;
	
	@Column(name="categorycode",length = 25)	
	private String assetCategoryId;
	
	private boolean cancel;
	
	@Column(name="createdby",length = 25)
	private String createdby;
	
	@Column(name="modifiedby",length = 25)
	private String modifiedby;
	
	@Column(name="cancelremarks",length = 50)
	private String cancelremarks;
	
	private boolean active;
	
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
