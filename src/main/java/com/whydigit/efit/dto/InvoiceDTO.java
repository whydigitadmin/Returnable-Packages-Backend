package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

	private Long id;
	private Long orgId;
	private String createdBy;
	private String poDate;
	private String poNumber;
	private String companyAddress;
	private String vendorAddress;
	private String deliveryAddress;
	private String termsAndConditions;
	private Long subtotal;
	private Integer sgst;
	private Integer cgst;
	private Long total;
	private String gstType;
	private Integer igst;
	private String modifiedBy;

	// private String logo;
//	private int logoWidth;
//	private String title;
//	private String companyName;
//	private String name;
//	private String poDate;
//	private String poNo;
//	private String companyAddress;
//	private String companyAddress2;
//	private String companyCountry;
//	private String billTo;
//	private String clientName;
//	private String clientAddress;
//	private String clientAddress2;
//	private String clientCountry;
//	private String invoiceTitle;
//	private LocalDate invoiceDate = LocalDate.now();
//	private LocalDate invoiceDueDate;
//	private String productLineDescription;
//	private String productLineQuantity;
//	private String productLineQuantityRate;
//	private String productLineQuantityAmount;
//	private String notes;
//	private String term;
	
	private List<InvoiceProductLinesDTO> items;

}
