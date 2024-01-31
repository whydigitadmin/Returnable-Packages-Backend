package com.whydigit.efit.entity;
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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flow")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long orgId;
	private String flowName;
	private String emitter;
	private String receiver;
	private String orgin;
	private String destination;
    private boolean active;
    
    @JsonManagedReference
	@OneToMany(mappedBy="flowVO",cascade = CascadeType.ALL)
	private List<FlowDetailVO> flowDetailVO;
    
    @Embedded
    @Builder.Default
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();
}