package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
	
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
    private String invoiceTitleLabel;
    private String invoiceTitle;
    private String invoiceDateLabel;
    private LocalDate invoiceDate=LocalDate.now();
    private String invoiceDueDateLabel;
    private LocalDate invoiceDueDate;
    private String productLineDescription;
    private String productLineQuantity;
    private String productLineQuantityRate;
    private String productLineQuantityAmount;
    private String notesLabel;
    private String notes;
    private String taxLabel;
    private String taxLabel1;
    private String taxLabel2;
    private String term;
    private String termLabel;
    private String totalLabel;
    private String subTotalLabel;
	private Long orgId;
	private String createdBy;
	
    private List<InvoiceProductLinesDTO>productLines;

}
