package com.whydigit.efit.entity;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asset_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetItemVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String skuId;
	private String assetName;
	private int status;
	private LocalDateTime createdDateTime;
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "asset_id")
	private AssetVO assetVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
