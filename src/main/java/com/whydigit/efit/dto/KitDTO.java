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
public class KitDTO {
	private Long id;
	private String kitNo;
	private String kitDesc;
	private long orgId;
	private int partQuantity;
	private boolean active;
	private String createdBy;
	private List<KitAssetDTO> kitAssetDTO;
}
