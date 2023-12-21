
package com.whydigit.efit.entity;

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
@Table(name = "ps_stock_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String emitterStoreDays;
	private String emitterLineDays;
    private String inTransitDays;
    private String endUserLineStorageDays;
    private String endUserManufacturingLineDays;
    private String otherStorageDays;
    private String totalCycleTime;
    private String emptyPackagingReverseDays;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
