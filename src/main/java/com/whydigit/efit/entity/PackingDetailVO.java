
package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	private long partStudyId;
	private long orgId;
	private int partDimension;
	private int length;
	private int breath;
	private int height;
	private String partUnit;
	private String existingPart;
	private String currentPackingStudy;
	private String currentPackingChallenges;
	private String noOfParts;
	private String partSensitive;
	private String greasy;
	private String partOrientation;
	private String multiPartInSingleUnit;
	private String stacking;
	private String nesting;
	private String remarks;
	private String partDrawing;
	private String approvedPackingTechnicalDrawing;
	private String approvedCommercialContract;
	private Boolean active;
	private byte[] partImage;

	@Lob
	private byte[] existingPackingImage;

	@JsonBackReference
	@OneToOne
	@MapsId
    @JoinColumn(name = "partStudyId")
	private BasicDetailVO basicDetailVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
