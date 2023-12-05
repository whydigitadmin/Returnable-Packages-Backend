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
@Table(name = "cm_city")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String cityCode;
	private String country;
    private String cityName;
    private String state;
    private boolean active;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
