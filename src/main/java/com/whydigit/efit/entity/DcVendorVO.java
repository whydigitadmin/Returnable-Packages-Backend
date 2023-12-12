package com.whydigit.efit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="dcvendor")
public class DcVendorVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String vendor_to;
	private String warehouse_from;
	private String dc_no;
	private String delivery_mode;
	private String sales_order_id;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dc_date=new Date();
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dispatch_date;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date arrival_date;
	private float sub_total;
	private float shipping_charge;
	private float adjusment;
	private float total;
	private String customer_notes;
	private String terms_condition;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="dcvendor_id",referencedColumnName = "id")
	private List<DcVendorItemDetailsVO>itemDetails=new ArrayList<>();
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	

}
