package com.whydigit.efit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movement_stock_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementStockItemVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Long kitAssetId;
	private String assetName;
	private int assetQTY;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "movement_stock_id")
	private MovementStockVO movementStockVO;

}
