package com.innovez.sample.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="users")
@SuppressWarnings("serial")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic @Column(name="id")
	private Long id;
	
	@NotBlank @Basic @Column(name="username", unique=true)
	private String username;
	
	@NotBlank @Basic @Column(name="password")
	private String password;
	
	@Basic @Column(name="is_account_expired")
	private boolean accountExpired;
	
	@Basic @Column(name="is_account_locked")
	private boolean accountLocked;
	
	@Basic @Column(name="is_credentials_expired")
	private boolean credentialsExpired;
	
	@Basic @Column(name="is_enabled")
	private boolean enabled;

	@JoinTable(name="user_roles")
	@ElementCollection(fetch=FetchType.LAZY)
	@Column(name="role_name")
	@OrderColumn(name="role_order")
	private List<String> roles = new ArrayList<String>();
	
	public Long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}
	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
