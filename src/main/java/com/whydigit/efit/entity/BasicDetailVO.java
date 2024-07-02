
package com.whydigit.efit.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partstudy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicDetailVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "partstudygen")
	@SequenceGenerator(name = "partstudygen", sequenceName = "partstudyseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partstudyid")
	private Long refPsId;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "docdate")
	private LocalDate partStudyDate=LocalDate.now();
	@Column(name = "emitterid")
	private Long emitterId;
	@Column(name = "emitterdisplayname")
	private String emitterDisplayName;
	private String partName;
	@Column(name = "code")
	private String partNumber;
	@Column(name = "weight")
	private Float weight;
	@Column(name = "weightunit")
	private String weightUnit;
	@Column(name = "partvol")
	private int partVolume;
	@Column(name = "maxvol")
	private int highestVolume;
	@Column(name = "minvol")
	private int lowestVolume;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedBy;
	@Column(name = "docid")
	private String docid;
	private boolean cancel;
	private boolean active;
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
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@JsonManagedReference
	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private PackingDetailVO packingDetailVO;

	@JsonManagedReference
	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private LogisticsVO logisticsVO;

	@JsonManagedReference
	@OneToOne(mappedBy = "basicDetailVO", cascade = CascadeType.ALL)
	private StockDetailVO stockDetailVO;

}
