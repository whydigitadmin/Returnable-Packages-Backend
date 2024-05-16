
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
	@Column(name = "receiverid")
	private Long receiverId;
	@Column(name = "part", length = 50)
	private String partName;
	@Column(name = "code", length = 25)
	private String partNumber;
	@Column(name = "weight", precision = 4, scale = 2)
	private Float weight;
	@Column(name = "weightunit", length = 25)
	private String weightUnit;
	@Column(name = "partvol", length = 16)
	private int partVolume;
	@Column(name = "maxvol", length = 15)
	private int highestVolume;
	@Column(name = "minvol", length = 15)
	private int lowestVolume;
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifiedby", length = 25)
	private String modifiedBy;
	@Column(name = "docid", length = 30)
	private String docid;
	private boolean cancel;
	private boolean active;
	
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
