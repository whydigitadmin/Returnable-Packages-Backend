package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDTO {
 private Long id;
 private Long orgId;
 private String unit;
 private String createdBy;
 private String updatedBy;
 private boolean active;
	
}
