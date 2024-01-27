package com.project.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Role")
public class Role {
	@Id
	private int id;
	
	private String roleName;


	public Role(int id, String roleName) {

		this.id = id;
		this.roleName = roleName;
	}
}
