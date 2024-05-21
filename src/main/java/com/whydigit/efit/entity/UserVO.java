package com.whydigit.efit.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	private boolean isActive;
	private String lastLogin;
	@Enumerated(EnumType.STRING)
	private Role role;
	private long accessRightsRoleId;
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
	
	// Custom getter for accessWarehouse
    @JsonGetter("accessWarehouse")
    public List<String> getAccessWarehouseAsList() {
        return accessWarehouse != null ? Arrays.asList(accessWarehouse.split(",")) : null;
    }

    // Standard getters and setters
    public String getAccessWarehouse() {
        return accessWarehouse;
    }

    public void setAccessWarehouse(String accessWarehouse) {
        this.accessWarehouse = accessWarehouse;
    }
	
}
