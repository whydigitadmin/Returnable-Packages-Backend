package com.whydigit.efit.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedUpdatedDate {
	
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