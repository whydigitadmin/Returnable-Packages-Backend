package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.entity.DispatchDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchDTO {
	
	private Long id;
	private String docId;
	private String flow;
	private String invoiceNo;
	private LocalDate invoiceDate;
	private String dispatchRemarks;
	private String createdby;
	private Long orgId;
	private Long emitterId;	

	private List<DispatchDetailsDTO> dispatchDetailsDTO;

}
