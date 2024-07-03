package com.whydigit.efit.entity;

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
@Table(name = "po1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoVO1 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "po1gen")
	@SequenceGenerator(name = "po1gen", sequenceName = "po1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "po1id")
	private Long po1Id;
	@Column(name = "itemid")
	private String itemId;
	@Column(name = "description")
	private String description;
	@Column(name = "hsncode")
	private int hsnCode;
	@Column(name = "qty")
	private int qty;
	@Column(name = "rate", precision = 10, scale = 3)
	private Float rate;
	@Column(name = "exrate", precision = 10, scale = 2)
	private Float exRate;
	@Column(name = "amount", precision = 10, scale = 2)
	private Float amount;
	@Column(name = "currency")
	private String currency;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "poid")
	private PoVO poVO;

}
