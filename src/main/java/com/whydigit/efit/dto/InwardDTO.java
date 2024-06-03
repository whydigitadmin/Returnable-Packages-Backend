package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardDTO {
	private Long id;
	private Long issueItemId;
	private String status;
	private int returnQty;
	private int netQtyRecieved;

}
