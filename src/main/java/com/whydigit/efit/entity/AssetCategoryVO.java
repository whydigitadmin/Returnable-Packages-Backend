package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	
	
	@Column(name="orgid")
	private Long orgId;
	
	
	@Column(name="category")
	private String category;
	
	
	@Column(name="assettype")
	private String assetType;
	
	private boolean active;
	
	
	@Column(name="length")
	private float length;
	
	
	@Column(name="breath")
	private float breath;
	
	
	@Column(name="height")
	private float height;
	
	
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
	
	
	@JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }

    // Optionally, if you want to control serialization for 'cancel' field similarly
    @JsonGetter("cancel")
    public String getCancel() {
        return cancel ? "T" : "F";
    }
	
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
