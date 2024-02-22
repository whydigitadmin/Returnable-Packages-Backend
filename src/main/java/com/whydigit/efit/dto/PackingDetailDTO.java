package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingDetailDTO {
	private long orgId;
	private Long refPsId;
	private String partStudyId;
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
}
