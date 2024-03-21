package com.whydigit.efit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "kitgen")
	@SequenceGenerator(name = "kitgen", sequenceName = "kitseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="kitid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="cancel")
	private boolean cancel;
	
	@Column(name = "createdby", length = 25)
	private String createdBy;

	@Column(name = "modifiedby",length = 25)
	private String modifiedBy;
	
	@Column(name = "cancelremarks",length = 25)
	private String cancelRemarks;
	
	@Column(name = "kitcode",length = 25)
	private String kitCode;
	
	@Column(name = "partqty",length = 25)
	private int partQty;
	
	@Column(name = "block")
	private boolean block;
	
	@OneToMany(mappedBy = "kitVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<KitAssetVO> kitAssetVO;
	
	
	// Created on and Modified on
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss a")
	@Column(name="createdon",length = 25)
	private String createdon;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss a")
	@Column(name="modifiedon",length = 25)
	private String modifiedon;

	@PrePersist
	public void onSave() {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		this.createdon = dateFormat.format(currentDate);
		this.modifiedon = dateFormat.format(currentDate);
	}

	@PostLoad
	public void onUpdate() {
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		this.modifiedon = dateFormat.format(currentDate);
	}
}
