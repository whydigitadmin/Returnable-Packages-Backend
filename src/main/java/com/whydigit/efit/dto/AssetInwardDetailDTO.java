package com.whydigit.efit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetInwardDetailDTO {

private String skuDetail;
private String skucode;
private int skuQty;
private Float stockValue;
private String stockLocation;
private String binLocation;
private String tagCode;
private String rfId;
}
