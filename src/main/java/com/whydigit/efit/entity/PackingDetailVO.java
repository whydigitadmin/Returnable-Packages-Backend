
package com.whydigit.efit.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ps_packing_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingDetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int partDimension;
	private String partUnit;
	private String existingPart;
	private String currentPackingStudy;
	private String currentPackingChallenges;
	private String noOfParts;
	private String partSensitive;
	private String greasy;
	private String partOrientation;
	private String multiPartInSingleSocket;
	private String stacking;
	private String nesting;
	private String remarks;
	private String partDrawing;
	private String approvedPackingTechnicalDrawing;
	private String approvedCommercialContract;
	 @Lob
	    private byte[] partImage;
	 
	 @Lob
	    private byte[] existingPackingImage;
	
	
	private Boolean active;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
