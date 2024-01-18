package com.whydigit.efit.entity;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.whydigit.efit.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String gstRegStatus;
	private String gstNo;
	private String street1;
	private String street2;
	private String state;
	private String city;
	private String pincode;
	private String contactName;
	private String phoneNumber;
	private String addressType;
    private boolean primary;
    
//    @JsonManagedReference
//	@OneToMany(mappedBy="flowVO",cascade = CascadeType.ALL)
//	private List<FlowDetailVO> flowDetailVO;
    
    @Embedded
    @Builder.Default
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();
}
