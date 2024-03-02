package com.whydigit.efit.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_attachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAttachmentVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String fileName;
	private String type;
	private Long customerId;
}
