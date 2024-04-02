package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Po1DTO {
	private Long poId;
	private String itemId;
	private String description;
	private int hsnCode;
	private int qty;
	private Float rate;
	private Float exRate;
	private Float amount;
	private String currency;
}
