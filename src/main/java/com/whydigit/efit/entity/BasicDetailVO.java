
package com.whydigit.efit.entity;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ps_basic_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicDetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
	private String partStudyNo;
	private LocalDate partStudyDate;
	private String emitterId;
	private String receiverId;
	private String partName;
	private String partNumber;
	private float weight;
	private String weightUnit;
	private String partValue;
	private String highestValue;
	private String lowestValue;
	
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
