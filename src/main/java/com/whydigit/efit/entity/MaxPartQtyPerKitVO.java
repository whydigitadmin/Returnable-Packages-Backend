package com.whydigit.efit.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vw_max_partqty_perkit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxPartQtyPerKitVO {
	@Id
private long id;
	private long orgId;
	private long emitterId;
	private long partNumber;
	private long flowId;
	private String kitId;
	private int maxPartQty;
}





