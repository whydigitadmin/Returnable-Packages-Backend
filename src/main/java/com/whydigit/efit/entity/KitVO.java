package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private String partId;
	private String partQty;
	@OneToMany(mappedBy = "kitVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<KitAssetVO> kitAssetVO;

}
