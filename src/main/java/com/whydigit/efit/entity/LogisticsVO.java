
package com.whydigit.efit.entity;

import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	private UUID partStudyId;
	private Long orgId;
	private String avgLotSize;
	private String dispatchFrequency;
	private String diapatchTo;
	private String transpotationTo;

	@OneToOne
	private BasicDetailVO basicDetailVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
