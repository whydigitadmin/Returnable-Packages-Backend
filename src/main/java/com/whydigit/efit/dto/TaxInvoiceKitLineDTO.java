package com.whydigit.efit.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaxInvoiceKitLineDTO {
	
	private Long id;
	private LocalDate date;
    private String manifestNo;
    private String emitter;
    private String location;
    private String kitNo;
    private String kitQty;

}
