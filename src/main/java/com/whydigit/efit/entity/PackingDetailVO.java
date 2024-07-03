
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
import javax.persistence.Lob;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partstudypackgen")
	@SequenceGenerator(name = "partstudypackgen", sequenceName = "partstudypackseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "partstudy2id")
	private Long id;

	@Column(name = "refpsid")
	private Long refPsId;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "length", precision = 4, scale = 2)
	private Float length;

	@Column(name = "breath", precision = 4, scale = 2)
	private Float breath;

	@Column(name = "height", precision = 4, scale = 2)
	private Float height;

	@Column(name = "unit", length = 25)
	private String partUnit;

	@Column(name = "existingpart")
	private String existingPart;

	@Column(name = "cpc")
	private String currentPackingChallenges;

	@Column(name = "ppp")
	private String partsPerPackaging;

	@Column(name = "senstive")
	private String partSensitive;

	@Column(name = "partgreasy")
	private String partGreasy;

	@Column(name = "orientation")
	private String partOrientation;

	@Column(name = "mpsu")
	private String multiPartInSingleUnit;

	@Column(name = "stacking")
	private String stacking;

	@Column(name = "nesting")
	private String nesting;

	@Column(name = "remarks")
	private String remarks;

	@Lob
	@Column(name = "partimage", columnDefinition = "LONGBLOB")
	private byte[] partImage;
	@Lob
	@Column(name = "existingpackingimage", columnDefinition = "LONGBLOB")
	private byte[] existingPackingImage;
	@Lob
	@Column(name = "partdrawing", columnDefinition = "LONGBLOB")
	private byte[] partDrawing;
	@Lob
	@Column(name = "approvedcommercialcontract", columnDefinition = "LONGBLOB")
	private byte[] approvedCommercialContract;

	@Column(name = "technicaldrawing", columnDefinition = "LONGBLOB")
	private byte[] comercial;

	@JsonBackReference
	@OneToOne
	@MapsId
	@JoinColumn(name = "partstudyid")
	private BasicDetailVO basicDetailVO;
	@JsonManagedReference
	@OneToMany(mappedBy = "packingDetailVO", cascade = CascadeType.ALL)
	private List<ApprovedPackageDrawingVO> approvedPackageDrawingVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
