
package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ps_logistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsVO {

	@Id
	private Long refPsId;
	private String partStudyId;
	private Long orgId;
	private String avgLotSize;
	private String dispatchFrequency;
	private String diapatchTo;
	@JsonBackReference
	@OneToOne
	@MapsId
	@JoinColumn(name = "refPsId")
	private BasicDetailVO basicDetailVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
