package com.whydigit.efit.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whydigit.efit.entity.KitAssetVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitResponseDTO {
	private Long id;
	private String docId;
	private String kitNo;
	private String kitDesc;
	private long orgId;
	private String partId;
	private int partQty;	
	private boolean eflag;
	private Map<String, List<KitAssetVO>> kitAssetCategory = new HashMap<>();

}
