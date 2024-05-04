package com.whydigit.efit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "kitgen")
	@SequenceGenerator(name = "kitgen", sequenceName = "kitseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name="kitid")
	private Long id;
	
	@Column(name="orgid")
	private Long orgId;
	
	@Builder.Default
	private String scode="BNKIT";
	
	@Column(name="active")
	private boolean active;
	
	private String kno;
	
	@Column(name="cancel")
	private boolean cancel;
	
	@Column(name="finyear",length = 10)
	private String finyr;
	
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
	
	@Column(name ="eflag")
	private boolean eflag;
	
	@OneToMany(mappedBy = "kitVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	List<KitAssetVO> kitAssetVO;
	
	
	@Builder.Default
	@Embedded
	private CreatedUpdatedDate commonDate=new CreatedUpdatedDate();
}
