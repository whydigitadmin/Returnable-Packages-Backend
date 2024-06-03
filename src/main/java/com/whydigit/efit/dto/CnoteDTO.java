package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CnoteDTO {
	private Long cnoteId;
	private String cancelRemarks;
	private String code;
	private String description;
	private String serviceCode;
	private String rcode;
	private String ccode;
	private String rlEdger;
	private String clEdger;
}
