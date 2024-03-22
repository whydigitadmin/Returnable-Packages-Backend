
package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partstudy2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingDetailVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "partstudy2gen")
	@SequenceGenerator(name = "partstudy2gen", sequenceName = "partstudy2seqge", initialValue = 1000000001, allocationSize = 1)
	@Column(name="partstudy2id")
	private Long refPsId;
	@Column(name="orgId")
	private Long orgId;
	@Column(name="length",precision = 4,scale = 2)
	private Float length;
	@Column(name="breath",precision = 4,scale = 2)
	private Float breath;
	@Column(name="height",precision = 4,scale = 2)
	private Float height;
	@Column(name="unit",length =25)
	private String partUnit;
	@Column(name="existingPart",length =25)
	private String existingPart;
	@Column(name="cpc",length = 25)
	private String currentPackingChallenges;
	@Column(name="ppp",length =25)
	private String partsPerPackaging;
	@Column(name="sensitive",length =25)
	private String partSensitive;
	@Column(name="partgreasy",length =25 )
	private String partGreasy;
	@Column(name="orientation",length =25)
	private String partOrientation;
	@Column(name="mpsu",length = 25)
	private String multiPartInSingleUnit;
	@Column(name="stacking",length =25)
	private String stacking;
	@Column(name="nesting",length =25 )
	private String nesting;
	@Column(name="remarks",length =25)
	private String remarks;
	
	
	
	
	
	@Transient
	private List<PDAttachmentVO> partImage;
	@Transient
	private List<PDAttachmentVO> existingPackingImage;
	@Transient
	private List<PDAttachmentVO> partDrawing;
	@Transient
	private List<PDAttachmentVO> approvedCommercialContract;

	@JsonBackReference
	@OneToOne
	@MapsId
    @JoinColumn(name = "partstudyid")
	private BasicDetailVO basicDetailVO;
	@JsonManagedReference
	@OneToMany(mappedBy = "packingDetailVO",cascade = CascadeType.ALL)
	private List<ApprovedPackageDrawingVO> approvedPackageDrawingVO;
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
