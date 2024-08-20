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
@Table(name="invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "invoicegen")
	@SequenceGenerator(name = "invoicegen", sequenceName = "invoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "invoiceid")
	private Long id;
	@Column(name = "logo", columnDefinition = "LONGBLOB")
	private byte[] logo;
	@Column(name = "logowidth")
    private int logoWidth;
	@Column(name = "title")
    private String title;
	@Column(name = "companyname")
    private String companyName;
	@Column(name = "name")
    private String name;
	@Column(name = "companyaddress")
    private String companyAddress;
	@Column(name = "companyaddress2")
    private String companyAddress2;
	@Column(name = "companycountry")
    private String companyCountry;
	@Column(name = "billto")
    private String billTo;
	@Column(name = "clientname")
    private String clientName;
	@Column(name = "clientaddress")
    private String clientAddress;
	@Column(name = "clientaddress2")
    private String clientAddress2;
	@Column(name = "clientcountry")
    private String clientCountry;
	@Column(name = "invoicetitlelabel")
    private String invoiceTitleLabel;
	@Column(name = "invoicetitle")
    private String invoiceTitle;
	@Column(name = "invoicedatelabel")
    private String invoiceDateLabel;
	@Column(name = "invoicedate")
    private LocalDate invoiceDate;
	@Column(name = "invoiceduedatelabel")
    private String invoiceDueDateLabel;
	@Column(name = "invoiceduedate")
    private LocalDate invoiceDueDate;
	@Column(name = "productlinedescription")
    private String productLineDescription;
	@Column(name = "productlinequantity")
    private String productLineQuantity;
	@Column(name = "productlinequantityrate")
    private String productLineQuantityRate;
	@Column(name = "productlinequantityamount")
    private String productLineQuantityAmount;
	@Column(name = "noteslabel")
    private String notesLabel;
	@Column(name = "notes",length = 1000)
    private String notes;
	@Column(name = "taxlabel")
    private String taxLabel;
	@Column(name = "taxlabel1")
    private String taxLabel1;
	@Column(name = "taxlabel2")
    private String taxLabel2;
	@Column(name = "term",length = 1000)
    private String term;
	@Column(name = "termlabel")
    private String termLabel;
	@Column(name = "totallabel")
    private String totalLabel;
	@Column(name = "subtotallabel")
    private String subTotalLabel;
	
	
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String modifiedeBy;
	
	@Column(name="cancel")
	private boolean cancel=false;
	
	@OneToMany(mappedBy = "invoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<InvoiceProductLinesVO>productLines;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
