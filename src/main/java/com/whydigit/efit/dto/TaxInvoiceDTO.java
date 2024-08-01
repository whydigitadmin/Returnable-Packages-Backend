package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxInvoiceDTO {
	
	private Long id;
	private String logo;
	private int logoWidth;
	private String title;
	private String companyName;
	private String name;
	private String companyAddress;
	private String companyAddress2;
	private String companyCountry;
	private String billTo;
	private String clientName;
	private String clientAddress;
	private String clientAddress2;
	private String clientCountry;
	private String shipTo;
	private String sclientName;
	private String sclientAddress;
	private String sclientAddress2;
	private String sclientCountry;
	private String invoiceTitleLabel;
	private String invoiceTitle;
	private String invoiceDateLabel;
	private LocalDate invoiceDate;
	private String invoiceDueDateLabel;
	private LocalDate invoiceDueDate;
	private String productLineDescription;
	private String productLineQuantity;
	private String productLineQuantityRate;
	private String productLineQuantityAmount;
	private LocalDate kitLineDate;
	private String kitLineManifestNo;
	private String kitLineEmitter;
	private String kitLineLocation;
	private String kitLineKitNo;
	private String kitLineKitQty;
	private String subTotalLabel;
	private String taxLabel;
	private String taxLabel1;
	private String totalLabel;
	private String currency;
	private String notesLabel;
	private String notes;
	private String termLabel;
	private String term;
	private String payTo;
	private String bankName;
	private String accountName;
	private String accountNo;
	private String IFSC;
	private String payToLabel;
	private String bankNameLabel;
	private String accountNameLabel;
	private String accountNoLabel;
	private String IFSCLabel;
	private List<TaxInvoiceProductLineDTO> productLines;
    private List<TaxInvoiceKitLineDTO> kitLines;
	private String createdBy;
	private Long orgId;

}
