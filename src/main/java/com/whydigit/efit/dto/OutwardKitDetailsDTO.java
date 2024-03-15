package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutwardKitDetailsDTO {
	private int emitterOtwarId;
	private String kitNO;
	private int kitQty;
}
