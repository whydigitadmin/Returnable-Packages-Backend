package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetDTO {
	
	private Long id;
    private Long orgId;
    private String category;
    private String categoryCode;
    private String assetCodeId;
    private String assetName;
    private float length;
    private float breadth;
    private float height;
    private float weight;
    private int quantity;
    private String dimUnit;
    private String manufacturer;
    private String chargableWeight;
    private String brand;
    private String eanUpc;
    private String assetType;
    private String expectedLife;
    private String maintenancePeriod;
    private String expectedTrips;
    private String hsnCode;
    private String taxRate;
    private long skuFrom;
    private long skuTo;
    private String costPrice;
    private String sellPrice;
    private String scrapValue;
    private String modifiedBy;
    private String cancelRemarks;
    private String poNo;
    private boolean active;

}
