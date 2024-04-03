package com.whydigit.efit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PodDTO {
	private Long podId;
	private Long orgId;
	private String docId;
	private String refNo;
	private String kitCode;
	private int kitQty;
	private int kitRqty;
	private boolean cancel;
	private boolean active;
    private LocalDate refDate;
    private LocalDate docdate;
    
	private List<Pod1DTO> pod1DTO;
	private List<Pod2DTO> pod2DTO;

}
