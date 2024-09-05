package com.whydigit.efit.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAdviceDTO {

	private Long id;
	private String companyAddress;
	private String billerName;
	private String billerAddress;
	private String date;
	private String paymentMode;
	private Long orgId;
	private String createdBy;
	private String modifiedBy;
	private Long amount;
	
	private List<PaymentAdviceBillDetailsDTO> paymentAdviceBillDetailsDTO;
	private List<PaymentAdviceDetailsDTO> paymentAdviceDetailsDTO;
}
