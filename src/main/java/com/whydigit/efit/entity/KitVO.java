package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
@Table(name = "kit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitVO {
	@Id
	private String id;
	private long orgId;
	@OneToMany(mappedBy = "kitVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<KitAssetVO> kitAssetVO;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
