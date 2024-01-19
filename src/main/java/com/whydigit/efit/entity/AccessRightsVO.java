package com.whydigit.efit.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "access_rights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessRightsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty(message = "Role is Required")
	private String role;
	@NotNull(message = "OrganizationId is Required")
	private long orgId;
	private int masters;
	private int inbound;
	private int outbound;
	private int partStudy;
	private int sales;
	private int tickets;
	private int expences;
	private int lifeCycleManagement;
	private int reports;

}
