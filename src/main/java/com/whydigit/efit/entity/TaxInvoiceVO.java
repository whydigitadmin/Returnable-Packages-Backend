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

	@Column(name = "title")
	private String title;

	@Column(name = "companyname", length = 1000)
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

	@Column(name = "shipto")
	private String shipTo;

	@Column(name = "sclientname")
	private String sclientName;

	@Column(name = "sclientaddress")
	private String sclientAddress;

	@Column(name = "sclientaddress2")
	private String sclientAddress2;

	@Column(name = "sclientcountry")
	private String sclientCountry;

	@Column(name = "invoicetitlelabel")
	private String invoiceTitleLabel;

	@Column(name = "invoicetitle")
	private String invoiceTitle;

	@Column(name = "invoicedatelabel")
	private String invoiceDateLabel;

	@Column(name = "invoicedate")
	private String invoiceDate;

	@Column(name = "invoiceduedatelabel")
	private String invoiceDueDateLabel;

	@Column(name = "invoiceduedate")
	private String invoiceDueDate;

	@Column(name = "productlinedescription")
	private String productLineDescription;

	@Column(name = "productlinequantity")
	private String productLineQuantity;

	@Column(name = "productlinequantityrate")
	private String productLineQuantityRate;

	@Column(name = "productlinequantityamount")
	private String productLineQuantityAmount;

	@Column(name = "kitlinedate")
	private String kitLineDate;

	@Column(name = "kitlinemanifestno")
	private String kitLineManifestNo;

	@Column(name = "kitlineemitter")
	private String kitLineEmitter;

	@Column(name = "kitlinelocation")
	private String kitLineLocation;

	@Column(name = "kitlinekitno")
	private String kitLineKitNo;

	@Column(name = "kitlinekitqty")
	private String kitLineKitQty;

	@Column(name = "subtotalabel")
	private String subTotalLabel;

	@Column(name = "taxlabel")
	private String taxLabel;

	@Column(name = "taxlabel1")
	private String taxLabel1;

	@Column(name = "totallabel")
	private String totalLabel;

	@Column(name = "currency")
	private String currency;

	@Column(name = "noteslabel")
	private String notesLabel;

	@Column(name = "notes")
	private String notes;

	@Column(name = "termlabel")
	private String termLabel;

	@Column(name = "term", length = 1000)
	private String term;

	@Column(name = "payto")
	private String payTo;

	@Column(name = "bankname")
	private String bankName;

	@Column(name = "accountname")
	private String accountName;

	@Column(name = "accountno")
	private String accountNo;

	@Column(name = "ifsc")
	private String IFSC;

	@Column(name = "paytolabel")
	private String payToLabel;

	@Column(name = "banknamelabel")
	private String bankNameLabel;

	@Column(name = "accountnamelabel")
	private String accountNameLabel;

	@Column(name = "accountnolabel")
	private String accountNoLabel;

	@Column(name = "ifscLabel")
	private String IFSCLabel;
	
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "invoicenolabel")
	private String invoiceNoLabel;
	@Column(name = "terms")
	private String terms;
	@Column(name = "termslabel")
	private String termsLabel;
	@Column(name = "duedatelabel")
	private String dueDateLabel;
	@Column(name = "duedate")
	private String dueDate;
	@Column(name = "servicemonthlabel")
	private String serviceMonthLabel;
	@Column(name = "servicemonth")
	private String serviceMonth;
	@Column(name = "placesupplylabel")
	private String placeSupplyLabel;
	@Column(name = "placesupply")
	private String placeSupply;
	
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
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



