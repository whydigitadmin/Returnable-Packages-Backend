
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
@Table(name = "ps_stock_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailVO {
	@Id
	private Long refPsId;
	private String partStudyId;
	private long orgId;
	private String emitterStoreDays;
	private String emitterLineDays;
	private String inTransitDays;
	private String endUserLineStorageDays;
	private String endUserManufacturingLineDays;
	private String otherStorageDays;
	private String totalCycleTime;
	private String emptyPackagingReverseDays;

	@JsonBackReference
	@OneToOne
	@MapsId
	@JoinColumn(name = "refPsId")
	private BasicDetailVO basicDetailVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
