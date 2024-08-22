package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vw_emitter_inward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VwEmitterInwardVO {
	@Id
	private Long id;
	private Long requestId;
	private Long orgId;
	private LocalDate requestedDate;
	private LocalDate demandDate;
	private Long emitterId;
	private String flowId;
	private Long warehouseLocationId;
	private String warehouseLocation;
	private String flowName;
	private Long kitQty;
	private String kitName;
	private String partName;
	private int partQty;
	private LocalDate reachedDate;
	private Long issueItemStatus;
	private int issuedQty;
	private int netQtyRecieved;
	private int returnQty;
	private long issueItemInwardId;
	private long itemId;
	private String status;
	private String partNo;
	private LocalDateTime approvedDate;
	private boolean approvedStatus;
	private boolean netRecAcceptStatus;

}
