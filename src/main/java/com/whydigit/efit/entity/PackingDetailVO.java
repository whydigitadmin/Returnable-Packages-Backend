
package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ps_packing_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingDetailVO {

	@Id
	private Long refPsId;
	private String partStudyId;
	private Long orgId;
	private Float length;
	private Float breath;
	private Float height;
	private String partUnit;
	private String existingPart;
	private String currentPackingChallenges;
	private String partsPerPackaging;
	private String partSensitive;
	private String partGreasy;
	private String partOrientation;
	private String multiPartInSingleUnit;
	private String stacking;
	private String nesting;
	private String remarks;
	private String partImage;
	private String existingPackingImage;
	private String partDrawing;
	private String approvedCommercialContract;

	@JsonBackReference
	@OneToOne
	@MapsId
    @JoinColumn(name = "refPsId")
	private BasicDetailVO basicDetailVO;
	@JsonManagedReference
	@OneToMany(mappedBy = "packingDetailVO",cascade = CascadeType.ALL)
	private List<ApprovedPackageDrawingVO> approvedPackageDrawingVO;
	

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
