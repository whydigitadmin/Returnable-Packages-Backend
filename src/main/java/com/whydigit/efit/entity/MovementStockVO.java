package com.whydigit.efit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movement_stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementStockVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Long emitterId;
	private String emitterName;
	private Long flowId;
	private String flowName;
	private String KitName;
	private int qty;
	private MovementType type;
	private String movementDate;
	private Long orgId;
	@JsonManagedReference
	@OneToMany(mappedBy = "movementStockVO", cascade = CascadeType.ALL)
	private List<MovementStockItemVO> MovementStockItemVO;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
