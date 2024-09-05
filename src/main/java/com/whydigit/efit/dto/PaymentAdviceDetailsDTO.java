package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAdviceDetailsDTO {

	private Long id;
	private String paymentMode;
	private String accountNo;
	private String bank;
	private String utrNo;
	private String paymentDate;
	private String issuedFrom;
	private Long paymentAmount;

}
