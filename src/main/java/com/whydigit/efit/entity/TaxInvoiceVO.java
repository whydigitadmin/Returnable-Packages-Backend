package com.whydigit.efit.entity;

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
@Table(name="taxinvoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxInvoiceVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "taxinvoicegen")
	@SequenceGenerator(name = "taxinvoicegen", sequenceName = "taxinvoiceseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "taxinvoiceid")
	private Long id;

	@Column(name = "logo", columnDefinition = "LONGBLOB")
	private byte[] logo;

	@Column(name = "logowidth")
	private int logoWidth;

	@Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "companyname", length = 1000, columnDefinition = "TEXT")
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

    @Column(name = "shipto", columnDefinition = "TEXT")
    private String shipTo;

    @Column(name = "sclientname", columnDefinition = "TEXT")
    private String sclientName;

    @Column(name = "sclientaddress", columnDefinition = "TEXT")
    private String sclientAddress;

    @Column(name = "sclientaddress2", columnDefinition = "TEXT")
    private String sclientAddress2;

    @Column(name = "sclientcountry", columnDefinition = "TEXT")
    private String sclientCountry;

    @Column(name = "invoicetitlelabel", columnDefinition = "TEXT")
    private String invoiceTitleLabel;

    @Column(name = "invoicetitle", columnDefinition = "TEXT")
    private String invoiceTitle;

    @Column(name = "invoicedatelabel", columnDefinition = "TEXT")
    private String invoiceDateLabel;

    @Column(name = "invoicedate", columnDefinition = "TEXT")
    private String invoiceDate;

    @Column(name = "invoiceduedatelabel", columnDefinition = "TEXT")
    private String invoiceDueDateLabel;

    @Column(name = "invoiceduedate", columnDefinition = "TEXT")
    private String invoiceDueDate;

    @Column(name = "productlinedescription", columnDefinition = "TEXT")
    private String productLineDescription;

    @Column(name = "productlinequantity", columnDefinition = "TEXT")
    private String productLineQuantity;

    @Column(name = "productlinequantityrate", columnDefinition = "TEXT")
    private String productLineQuantityRate;

    @Column(name = "productlinequantityamount", columnDefinition = "TEXT")
    private String productLineQuantityAmount;

    @Column(name = "kitlinedate", columnDefinition = "TEXT")
    private String kitLineDate;

    @Column(name = "kitlinemanifestno", columnDefinition = "TEXT")
    private String kitLineManifestNo;

    @Column(name = "kitlineemitter", columnDefinition = "TEXT")
    private String kitLineEmitter;

    @Column(name = "kitlinelocation", columnDefinition = "TEXT")
    private String kitLineLocation;

    @Column(name = "kitlinekitno", columnDefinition = "TEXT")
    private String kitLineKitNo;

    @Column(name = "kitlinekitqty", columnDefinition = "TEXT")
    private String kitLineKitQty;

    @Column(name = "subtotalabel", columnDefinition = "TEXT")
    private String subTotalLabel;

    @Column(name = "taxlabel", columnDefinition = "TEXT")
    private String taxLabel;

    @Column(name = "taxlabel1", columnDefinition = "TEXT")
    private String taxLabel1;

    @Column(name = "taxlabel2", columnDefinition = "TEXT")
    private String taxLabel2;

    @Column(name = "totallabel", columnDefinition = "TEXT")
    private String totalLabel;

    @Column(name = "currency", columnDefinition = "TEXT")
    private String currency;

    @Column(name = "noteslabel", columnDefinition = "TEXT")
    private String notesLabel;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "termlabel", columnDefinition = "TEXT")
    private String termLabel;

    @Column(name = "term", length = 1000, columnDefinition = "TEXT")
    private String term;

    @Column(name = "payto", columnDefinition = "TEXT")
    private String payTo;

    @Column(name = "bankname", columnDefinition = "TEXT")
    private String bankName;

    @Column(name = "accountname", columnDefinition = "TEXT")
    private String accountName;

    @Column(name = "accountno", columnDefinition = "TEXT")
    private String accountNo;

    @Column(name = "ifsc", columnDefinition = "TEXT")
    private String IFSC;

    @Column(name = "paytolabel", columnDefinition = "TEXT")
    private String payToLabel;

    @Column(name = "banknamelabel", columnDefinition = "TEXT")
    private String bankNameLabel;

    @Column(name = "accountnamelabel", columnDefinition = "TEXT")
    private String accountNameLabel;

    @Column(name = "accountnolabel", columnDefinition = "TEXT")
    private String accountNoLabel;

    @Column(name = "ifscLabel", columnDefinition = "TEXT")
    private String IFSCLabel;

    @Column(name = "invoiceno", columnDefinition = "TEXT")
    private String invoiceNo;

    @Column(name = "invoicenolabel", columnDefinition = "TEXT")
    private String invoiceNoLabel;

    @Column(name = "terms", columnDefinition = "TEXT")
    private String terms;

    @Column(name = "termslabel", columnDefinition = "TEXT")
    private String termsLabel;

    @Column(name = "duedatelabel", columnDefinition = "TEXT")
    private String dueDateLabel;

    @Column(name = "duedate", columnDefinition = "TEXT")
    private String dueDate;

    @Column(name = "servicemonthlabel", columnDefinition = "TEXT")
    private String serviceMonthLabel;

    @Column(name = "servicemonth", columnDefinition = "TEXT")
    private String serviceMonth;

    @Column(name = "placesupplylabel", columnDefinition = "TEXT")
    private String placeSupplyLabel;

    @Column(name = "placesupply", columnDefinition = "TEXT")
    private String placeSupply;

    @Column(name = "createdby", columnDefinition = "TEXT")
    private String createdBy;

    @Column(name = "modifiedby", columnDefinition = "TEXT")
    private String modifiedBy;

    @Column(name = "orgid")
    private Long orgId;
	
	private boolean cancel=false;
	
	@OneToMany(mappedBy = "taxInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<TaxInvoiceProductLineVO> productLines;
	
	@OneToMany(mappedBy = "taxInvoiceVO", cascade = CascadeType.ALL)
	@JsonManagedReference
    private List<TaxInvoiceKitLineVO> kitLines;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}



