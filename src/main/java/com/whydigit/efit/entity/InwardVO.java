package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issue_item_inward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String status;
	private long returnQty;
	private long netQtyRecieved;
	
	@JsonBackReference
	@OneToOne
    @JoinColumn(name = "issueItemId")
	private IssueItemVO issueItemVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
