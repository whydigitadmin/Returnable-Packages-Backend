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
@Table(name = "partstudy5")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PDAttachmentVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "partstudy5gen")
	@SequenceGenerator(name = "partstudy5gen", sequenceName = "partstudy5seqge", initialValue = 1000000001, allocationSize = 1)
	@Column(name ="partstudy5id")
	private long id;
	@Column(name ="filename",length=50)
	private String fileName;
	@Column(name ="type",length =50)
	private String type;
	@Column(name ="partystudy5rowid",length=15)
	private Long refPsId;
	
}
