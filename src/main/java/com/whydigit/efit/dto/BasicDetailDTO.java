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
	private String partStudyId;
	private long orgId;
	private LocalDate partStudyDate;
	private String emitterId;
	private String receiverId;
	private String partName;
	private String partNumber;
	private float weight;
	private String weightUnit;
	private String partVolume;
	private String highestVolume;
	private String lowestVolume;
	
}
