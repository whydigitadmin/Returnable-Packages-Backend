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
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "usersdetails")
public class UsersDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userdetails_id;
	private String employee_type;
	private String emploee_id;
	private String employee_name;
	private String username;
	private String password;
	private String conatct_no;
	private String email_id;
	private String customer_assigned;
	private String location_assigned;
	private boolean active;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
