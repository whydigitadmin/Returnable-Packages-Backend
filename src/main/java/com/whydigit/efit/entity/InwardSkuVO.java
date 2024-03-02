package com.whydigit.efit.entity;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inwardsku_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InwardSkuVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private Long retManifestNo;
	private LocalDateTime rmDate;
	private String kitNo;
	private String flowName;
	private String transporter;
	private String wayBillNo;
	private Long dehireQty;
	private Long netRecdQty;
	private Long repair;
	private Long scrap;
	private Long tat;
	private String partName;
	private String partNo;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
