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
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="binretrievaldetails")
public class BinRetrievalDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "binretrievaldetailssgen")
	@SequenceGenerator(name = "binretrievaldetailsgen", sequenceName = "binretrievaldetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "binretrievaldetailsid")
	private Long id;
	@Column(name = "category")
	private String category;
	@Column(name = "assetcode")
	private String assetCode;
	@Column(name = "asset")
	private String asset;
	@Column(name = "invqty")
	private int invqty;
	@Column(name = "recqty")
	private int recqty;

	@ManyToOne
	@JoinColumn(name = "binretrievalid")
	@JsonBackReference
	private BinRetrievalVO binRetrievalVO;


}
