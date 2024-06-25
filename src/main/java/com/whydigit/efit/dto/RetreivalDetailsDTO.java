package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RetreivalDetailsDTO {
	
	private String outwardDocId;
	private String outwardDocDate;
	private String stockbranch;

}
