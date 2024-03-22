package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="dmap")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DmapVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dmapgen")
	@SequenceGenerator(name = "dmapgen", sequenceName = "dmapseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="dmapid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	private boolean cancel=false;
	private boolean active;
	
	@Column(name="cancelremarks",length = 50)
	private String cancelRemarks;
	
	@Column(name="createdby",length = 25)
	private String createdBy;
	
	@Column(name="modifiedby",length = 25)
	private String modifiedBy;
	
	@Column(name="finyear",length = 4)
	private String finYear;
	
	@Column(name="fromdate")
	private LocalDate fromDate;
	
	@Column(name="todate")
	private LocalDate toDate;
	
	@Column(name="extendeddate")
	private LocalDate extDate;
	
	@OneToMany(mappedBy = "dmapVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<DmapDetailsVO> dmapDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();

}
