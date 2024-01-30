package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenderVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
	private String venderType;
	private String firstName;
	private String lastName;
	private String venderOrgName;
	private String displyName;
	private String email;
	private String phoneNumber;
	private boolean active;
	private boolean venderActivePortal;

//	@OneToMany(mappedBy="flowVO",cascade = CascadeType.ALL)
//	private List<FlowDetailVO> flowDetailVO;

	@Embedded
	private CreatedUpdatedDate commonDate;
}
