package com.whydigit.efit.dto;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedUpdatedDate {
	private Date createdon;
	private Date modifiedon;

	@PrePersist
	public void onSave() {
		Date currentDate = new Date();
		this.createdon = currentDate;
		this.modifiedon = currentDate;
	}

	@PostLoad
	public void onUpdate() {
		Date currentDate = new Date();
		this.modifiedon = currentDate;
	}

}