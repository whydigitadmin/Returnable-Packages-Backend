package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DmapDetailsDTO {
	
	private String scode;
	private String prefix;
	private int sequence;
	private String sufix;
	private String type;

}
