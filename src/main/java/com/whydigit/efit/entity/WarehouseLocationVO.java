
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
@Table(name = "warehouse_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseLocationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
	private String warehouselocation;
	private boolean active;
	private String createdBy;
	private String updatedBy;
	private String unitName;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
