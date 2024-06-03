package com.whydigit.efit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermsAndConditionsDTO {
    private Long termsId;
	private Long orgId;
	private String sCode;
	private String termsCode;
	private String details;
	private String printRemarks;
	private LocalDate effectiveFrom;
	private LocalDate effectiveTo;
}
