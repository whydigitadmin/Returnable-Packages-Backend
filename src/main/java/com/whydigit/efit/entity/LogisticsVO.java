
package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ps_logistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String avgLotSize;
	private String dispatchFrequency;
	private String diapatchTo;
	private String transpotationTo;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
