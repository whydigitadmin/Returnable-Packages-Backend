package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tagging")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetTaggingVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "tagginggen")
	@SequenceGenerator(name = "tagginggen", sequenceName = "taggingseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="taggingid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	private String scode="ASTAG";
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="docid")
	private String docid;
	
	@Column(name="docdate")
	private LocalDate docDate;
	
	@Column(name="cancel")
	private boolean cancel;
	
	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String modifiedBy;
	
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	
	@Column(name = "assetcategory")
	private String category;

	@Column(name = "assetcode")
	private String assetCode;
	
	@Column(name = "asset")
	private String asset;
	
	@Column(name = "seqfrom")
	private int seqFrom;
	
	@Column(name = "seqto")
	private int seqTo;
	
	public String finyr;
	
	@Column(name = "pono")
	private String poNo;
	
	@Column(name="podate")
	private LocalDate poDate;
	
	
	@OneToMany(mappedBy = "taggingVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<AssetTaggingDetailsVO> taggingDetails;
	
	

	@Embedded
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();
	
	@JsonGetter("active")
    public String getActive() {
        return active ? "Active" : "In-Active";
    }

    // Optionally, if you want to control serialization for 'cancel' field similarly
    @JsonGetter("cancel")
    public String getCancel() {
        return cancel ? "T" : "F";
    }
	
	
	@PrePersist
    private void setDefaultFinyr() {
        // Execute the logic to set the default value for finyr
        String fyFull = calculateFinyr();
        this.finyr = fyFull;
    }

    private String calculateFinyr() {
        // Logic to calculate finyr based on the provided SQL query
        String currentMonthDay = LocalDate.now().format(DateTimeFormatter.ofPattern("MMdd"));
        String fyFull = (currentMonthDay.compareTo("0331") > 0) ?
                            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")) :
                            LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyy"));
        return fyFull;
    }

	

}
