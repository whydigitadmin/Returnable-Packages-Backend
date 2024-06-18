package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OemBinInwardDetailsDTO {

	private String outwardDocId;
	private String outwardDocDate;
	private String partName;
	private String partNo;
	private int allotedQty;
	private String kitNo;
	private int receivedKitQty;
}
