package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAdviceBillDetailsDTO {
	
	private Long id;
	private String billReference;
	private String date;
	private Long amount;
}
