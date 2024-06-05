/**
 * 
 */
package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="dispatchdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchDetailsVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dispatchdetailsgen")
	@SequenceGenerator(name = "dispatchdetailsgen", sequenceName = "dispatchdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "dispatchdetailsid")
	private Long id;
	@Column(name = "binoutwarddocid")
	private String binOutDocid;
	@Column(name = "binoutdocdate")
	private LocalDate binOutDocDate;
	@Column(name = "partname")
	private String partName;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "kitno")
	private String kitNo;
	@Column(name = "qty")
	private int qty;
	
	@ManyToOne
	@JoinColumn(name = "dispatchid")
	@JsonBackReference
	private DispatchVO dispatchVO;

}
