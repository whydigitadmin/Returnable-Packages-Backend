package com.whydigit.efit.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.whydigit.efit.dto.CreatedUpdatedDate;
import com.whydigit.efit.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private boolean loginStatus;
	private String accessaddId;
	private String accessWarehouse;
	private String accessFlowId;
	private Long pNo;
	@Column(name = "receiverid")
	private Long receiverId;
	private boolean isActive;
	private String lastLogin;
	private String createdBy;
	@Column(name = "modifiedBy")
	private String updatedBy;
	@Enumerated(EnumType.STRING)
	private Role role;
	private long accessRightsRoleId;

	private boolean viewFlag;

	@ManyToOne
	@JoinColumn(name = "orgId")
	private OrganizationVO organizationVO;
	@OneToOne
	@JoinColumn(name = "address_id")
	private UserAddressVO userAddressVO;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	private Date accountRemovedDate;
	@ManyToOne
	@JoinColumn(name = "emitter_id")
	private CustomersVO customersVO;

	@JsonGetter("active")
	public String getActive() {
		return isActive ? "Active" : "In-Active";
	}

	public boolean isActive() {
		return isActive;
	}

	@JsonGetter("accessWarehouse")
	public List<Long> getAccessWarehouseAsList() {
		if (accessWarehouse != null && !accessWarehouse.isEmpty()) {
			return Arrays.stream(accessWarehouse.split(",")).map(Long::parseLong).collect(Collectors.toList());
		}
		return null;
	}

	// Standard getters and setters
	public String getAccessWarehouse() {
		return accessWarehouse;
	}

	public void setAccessWarehouse(String accessWarehouse) {
		this.accessWarehouse = accessWarehouse;
	}

	@JsonGetter("accessFlowId")
	public List<Long> getAccessFlowIdAsList() {
		if (accessFlowId != null && !accessFlowId.isEmpty()) {
			return Arrays.stream(accessFlowId.split(",")).map(Long::parseLong).collect(Collectors.toList());
		}
		return null;
	}

	// Standard getters and setters
	public String getAccessFlowId() {
		return accessFlowId;
	}

	public void setAccessFlowId(String accessFlowId) {
		this.accessFlowId = accessFlowId;
	}

}
