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
	private String emitter;
	private long emitterId;
	private String orgin;
	private String createdBy;
	private String destination;
    private String warehouseLocation;
    private Long warehouseId;  
    private String retrievalWarehouseLocation;
	private Long retrievalWarehouseId;
	private boolean active;
	private String modifiedBy;
	private List<FlowDetailDTO> flowDetailDTO;
}
