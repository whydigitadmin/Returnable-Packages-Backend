package com.whydigit.efit.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;

import com.whydigit.efit.entity.KitAssetVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KitResponseDTO {
	private String kitCode;
	private long orgId;
	private String partId;
	private int partQty;	
	private Map<String, List<KitAssetVO>> kitAssetCategory = new HashMap<>();

}
