package com.project.ecommerce.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserDto {
	private int userId;
	@NotEmpty
	@Size(min = 2, message = "Name must be minimum 2 characters")
    private String userName;
	@NotBlank
	@Size(min = 2, message = "Password must be minimum 2 characters")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    private String password;
    @Email(message = "Invalid email")
	@NotEmpty
    private String email;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ "]";
	}
    
    
}
