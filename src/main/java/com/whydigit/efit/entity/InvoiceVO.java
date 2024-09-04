package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "invoicegen")
	@SequenceGenerator(name = "invoicegen", sequenceName = "invoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "invoiceid")
	private Long id;

	@Column(name = "podate", columnDefinition = "TEXT")
	private String poDate;

	@Column(name = "pono", columnDefinition = "TEXT")
	private String poNumber;

	@Column(name = "companyaddress", columnDefinition = "TEXT")
	private String companyAddress;

	@Column(name = "vendoraddress", columnDefinition = "TEXT")
	private String vendorAddress;

	@Column(name = "deliveryaddress", columnDefinition = "TEXT")
	private String deliveryAddress;

	@Column(name = "termsandconditions", length = 1000, columnDefinition = "TEXT")
	private String termsAndConditions;

	@Column(name = "subtotal")
	private Long subtotal;

	@Column(name = "sgst")
	private Integer sgst;

	@Column(name = "cgst")
	private Integer cgst;

	@Column(name = "total")
	private Long total;

	@Column(name = "gsttype", length = 1000, columnDefinition = "TEXT")
	private String gstType;

	@Column(name = "igst")
	private Integer igst;

	@Column(name = "orgid")
	private Long orgId;
	
	@Column(name = "createdby", columnDefinition = "TEXT")
	private String createdBy;
	
	@Column(name = "modifiedby", columnDefinition = "TEXT")
	private String modifiedBy;
	
	@Column(name = "cancel")
	private boolean cancel = false;
	
//    @Column(name = "logo", columnDefinition = "LONGBLOB")
//    private byte[] logo;
//
//    @Column(name = "logowidth")
//    private int logoWidth;
//
//    @Column(name = "title", columnDefinition = "TEXT")
//    private String title;
	
//	  @Column(name = "companyname", columnDefinition = "TEXT")
//    private String companyName;

//    @Column(name = "name", columnDefinition = "TEXT")
//    private String name;

//    @Column(name = "companyaddress2", columnDefinition = "TEXT")
//    private String companyAddress2;

//	@Column(name = "companycountry", columnDefinition = "TEXT")
//	private String companyCountry;
//
//	@Column(name = "billto", columnDefinition = "TEXT")
//	private String billTo;
//
//	@Column(name = "clientname", columnDefinition = "TEXT")
//	private String clientName;
//
//	@Column(name = "clientaddress", columnDefinition = "TEXT")
//	private String clientAddress;
//
//	@Column(name = "clientaddress2", columnDefinition = "TEXT")
//	private String clientAddress2;
//
//	@Column(name = "clientcountry", columnDefinition = "TEXT")
//	private String clientCountry;
//
//	@Column(name = "invoicetitle", columnDefinition = "TEXT")
//	private String invoiceTitle;
//
//	@Column(name = "invoicedate")
//	private LocalDate invoiceDate;
//
//	@Column(name = "invoiceduedate")
//	private LocalDate invoiceDueDate;
//
//	@Column(name = "productlinedescription", columnDefinition = "TEXT")
//	private String productLineDescription;
//
//	@Column(name = "productlinequantity", columnDefinition = "TEXT")
//	private String productLineQuantity;
//
//	@Column(name = "productlinequantityrate", columnDefinition = "TEXT")
//	private String productLineQuantityRate;
//
//	@Column(name = "productlinequantityamount", columnDefinition = "TEXT")
//	private String productLineQuantityAmount;
//
//	@Column(name = "notes", length = 1000, columnDefinition = "TEXT")
//	private String notes;


	@OneToMany(mappedBy = "invoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InvoiceProductLinesVO> productLines;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
