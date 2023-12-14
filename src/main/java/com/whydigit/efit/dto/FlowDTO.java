package com.whydigit.efit.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowDTO {

	private int id;
	private String flowName;
	private String flowInfo;
	private String flowType;
	private String orgin;
	private String destination;
    private boolean active;
    private List<FlowDetailDTO> flowDetailDTO;
}
