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
	private String id;
	private long orgId;
	private List<KitAssetDTO> kitAssetDTO;
}
