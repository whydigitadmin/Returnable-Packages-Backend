package com.whydigit.efit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicDetailDTO {

	private Long refPsId;
	private Long orgId;
	private LocalDate partStudyDate;
	private Long emitterId;
//	private Long receiverId;
	private String partName;
	private String partNumber;
	private Float weight;
	private String weightUnit;
	private int partVolume;
	private int highestVolume;
	private int lowestVolume;
	private boolean active;
	private String createdBy;
	private String modifiedBy;
	
	
}
