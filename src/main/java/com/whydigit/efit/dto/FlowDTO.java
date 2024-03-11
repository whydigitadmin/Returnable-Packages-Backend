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
	private Long id;
	private long orgId;
	private Long receiverId;
	private String flowName;
//	private String receiver;
	private String emitter;
	private long emitterId;
//	private String flowInfo;
//	private String flowType;
	private String orgin;
	private String destination;
    private String warehouseLocation;
    private Long warehouseId;      
	private boolean active;
	private List<FlowDetailDTO> flowDetailDTO;
}
