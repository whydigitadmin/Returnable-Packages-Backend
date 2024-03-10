package com.whydigit.efit.entity;

import java.time.LocalDate;

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
private long requestID;
private long orgId;
private LocalDate requestedDate;
private LocalDate demandDate;
private long emitterId;
private String flowTo;
private String flowName;
private long kitQty;
private String partName;
private long partQty;
private LocalDate reachedDate;
private long issueItemStatus;
private long issuedQty;
private long netQtyRecieved;
private long returnQty;
private String status;






}
