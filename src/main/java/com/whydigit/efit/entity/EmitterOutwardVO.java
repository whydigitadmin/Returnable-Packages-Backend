																																																																																																																																																																																																																																																																											package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emitter_outward")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmitterOutwardVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long orgId;
	private String location;
//	private String issuedTo;
//	private String partNo;
//	private String partName;
//	private String partQty;
	private String kitNo;
	private long kitQty;
	private long kitReturnqty;
	private int balancedKit;
	private String invoiceNo;
	private String cycleTime;
	private String previousDispatch;
	private String o2oTat;
	private boolean active;
    private LocalDate inwardConfirmDate;
	@JsonBackReference
	@OneToOne
    @JoinColumn(name = "issueItemId")
	private IssueItemVO issueItemVO;
	
	@OneToMany(mappedBy = "emitterOutwardVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<OutwardKitDetailsVO> OutwardKitDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
