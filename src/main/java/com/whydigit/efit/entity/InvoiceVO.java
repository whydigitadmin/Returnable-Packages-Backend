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

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "podate", columnDefinition = "TEXT")
    private String poDate;

    @Column(name = "podatelabel", columnDefinition = "TEXT")
    private String poDateLabel;

    @Column(name = "pono", columnDefinition = "TEXT")
    private String poNo;

    @Column(name = "ponolabel", columnDefinition = "TEXT")
    private String poNoLabel;

    @Column(name = "companyname", columnDefinition = "TEXT")
    private String companyName;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "companyaddress", columnDefinition = "TEXT")
    private String companyAddress;

    @Column(name = "companyaddress2", columnDefinition = "TEXT")
    private String companyAddress2;

    @Column(name = "companycountry", columnDefinition = "TEXT")
    private String companyCountry;

    @Column(name = "billto", columnDefinition = "TEXT")
    private String billTo;

    @Column(name = "clientname", columnDefinition = "TEXT")
    private String clientName;

    @Column(name = "clientaddress", columnDefinition = "TEXT")
    private String clientAddress;

    @Column(name = "clientaddress2", columnDefinition = "TEXT")
    private String clientAddress2;

    @Column(name = "clientcountry", columnDefinition = "TEXT")
    private String clientCountry;

    @Column(name = "invoicetitlelabel", columnDefinition = "TEXT")
    private String invoiceTitleLabel;

    @Column(name = "invoicetitle", columnDefinition = "TEXT")
    private String invoiceTitle;

    @Column(name = "invoicedatelabel", columnDefinition = "TEXT")
    private String invoiceDateLabel;

    @Column(name = "invoicedate")
    private LocalDate invoiceDate;

    @Column(name = "invoiceduedatelabel", columnDefinition = "TEXT")
    private String invoiceDueDateLabel;

    @Column(name = "invoiceduedate")
    private LocalDate invoiceDueDate;

    @Column(name = "productlinedescription", columnDefinition = "TEXT")
    private String productLineDescription;

    @Column(name = "productlinequantity", columnDefinition = "TEXT")
    private String productLineQuantity;

    @Column(name = "productlinequantityrate", columnDefinition = "TEXT")
    private String productLineQuantityRate;

    @Column(name = "productlinequantityamount", columnDefinition = "TEXT")
    private String productLineQuantityAmount;

    @Column(name = "noteslabel", columnDefinition = "TEXT")
    private String notesLabel;

    @Column(name = "notes", length = 1000, columnDefinition = "TEXT")
    private String notes;

    @Column(name = "taxlabel", columnDefinition = "TEXT")
    private String taxLabel;

    @Column(name = "taxlabel1", columnDefinition = "TEXT")
    private String taxLabel1;

    @Column(name = "taxlabel2", columnDefinition = "TEXT")
    private String taxLabel2;

    @Column(name = "term", length = 1000, columnDefinition = "TEXT")
    private String term;

    @Column(name = "termlabel", columnDefinition = "TEXT")
    private String termLabel;

    @Column(name = "totallabel", columnDefinition = "TEXT")
    private String totalLabel;

    @Column(name = "subtotallabel", columnDefinition = "TEXT")
    private String subTotalLabel;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "createdby", columnDefinition = "TEXT")
    private String createdBy;

    @Column(name = "modifiedby", columnDefinition = "TEXT")
    private String modifiedBy;

    @Column(name = "cancel")
    private boolean cancel = false;

    @OneToMany(mappedBy = "invoiceVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InvoiceProductLinesVO> productLines;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
