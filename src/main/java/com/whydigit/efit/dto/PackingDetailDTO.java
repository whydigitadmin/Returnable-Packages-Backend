package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingDetailDTO {
	private Long orgId;
	private Long refPsId;
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
	private byte[] partImage;
	private byte[] existingPackingImage;
	private byte[] partDrawing;
	private byte[] approvedCommercialContract;
	private byte[] comercial;

}
