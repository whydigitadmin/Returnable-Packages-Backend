package com.whydigit.efit.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer3")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAttachmentVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CustomerAttachmentVO")
	@SequenceGenerator(name = "CustomerAttachmentVO", sequenceName = "CustomerAttachmentVo", initialValue = 1000000001, allocationSize = 1)
	@Column(name="customer3id")
	private long id;
	@Column(name="filename",length = 25)
	private String fileName;
	@Column(name="type",length = 25)
	private String type;
	@Column(name="customerid")
	private Long customerId;
}
