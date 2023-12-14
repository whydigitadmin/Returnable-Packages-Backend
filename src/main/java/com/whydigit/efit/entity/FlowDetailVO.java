package com.whydigit.efit.entity;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flow_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowDetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String itemGroup;
	private String productToPack;
	private int quantity;
	private String rentalTerm;
	private String cycleTime;
    private boolean active;
    private String fixedRentalCharge;
    private String dhr;
    private String issueCharge;
    private String returnCharge;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "flow_id")
    private FlowVO flowVO;
    
    @Embedded
    @Builder.Default
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();
}