
package com.whydigit.efit.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "manufacturer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
	private String company;
	private String branch;
	private String address;
	private String email;
	private String contactPerson;
	private String designation;
	private String phoneNO;
	private String productionCapacity;
	private String notes;
	private boolean active;
//	@JsonManagedReference
//	@OneToMany(mappedBy = "manufacturerVO",cascade = CascadeType.ALL)
//    private ManufacturerProductVO manufacturerProductVO;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
