package com.whydigit.efit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "outward_view")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutwardView {
	@Id
	private Long outwardId;
	private Long rmNo;
	private LocalDate rmDate;
	private Long imNo;
	private LocalDate imDate;
	private String kitNumber;
	private String flowName;
	private String flowNumber;
	private int netQtyReceived; 
	private String cycleTime;
	private int kitReturnQTY;
	private Long orgId;
    private LocalDate inwardConfirmDate;
	private long emitterId;
	private int balanceQTY;
}
