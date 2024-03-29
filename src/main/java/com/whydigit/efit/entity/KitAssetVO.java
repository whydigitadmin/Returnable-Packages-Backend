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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kit2")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitAssetVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "kit2gen")
	@SequenceGenerator(name = "kit2gen", sequenceName = "kit2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="kit2id")
	private long id;
	
	@Column(name="kit2rowid")
	private int kit2RowId;
	
	@Column(name="assetcategory",length = 25)
	private String assetCategory;
	
	@Column(name="assetcode",length = 25)
	private String assetCodeId;
	
	@Column(name="asset",length = 25)
	private String assetName;
	
	@Column(name="quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "kitid")
	@JsonBackReference
	private KitVO kitVO;
}
