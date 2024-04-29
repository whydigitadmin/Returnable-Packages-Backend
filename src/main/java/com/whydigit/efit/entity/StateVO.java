package com.whydigit.efit.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "state")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "stategen")
	@SequenceGenerator(name = "stategen", sequenceName = "stateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="stateid")
	private Long id;
	@Column(name="code",length = 30)
	private String stateCode;
	@Column(name="state",length = 50)
	private String stateName;
	@Column(name="country",length = 30)
    private String country;
	@Column(name="active",length = 30)
    private boolean active;
	@Column(name="stateno",length = 30)
	private String stateNo;
	
	@Column(name="orgid")
	private Long orgId;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
