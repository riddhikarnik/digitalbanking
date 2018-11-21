package com.banking.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * This class uses Annotations for mapping
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "login", name = "users_unique_login_idx") })
public class User extends BaseEntity {

	@Column(name = "login", nullable = false, unique = true)
	private String loginId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "firstname", nullable = false)
	private String firstName;

	@Column(name = "lastname", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "creationtimestamp")
	private Date createdOn;

	@Column(name = "enabled", nullable = false)
	@NotNull
	private boolean enabled = true;

	@Enumerated(EnumType.STRING)
	@Column(name = "roletype", nullable = false)
	@NotNull
	private Role role;

	public User() {
	}

	public User(Integer userId) {
		super(userId);
	}

	public User(Integer userId, String login, String password, String firstName, String lastName, String email,
			boolean enabled, Role role) {
		super(userId);
		this.loginId = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.enabled = enabled;
		this.role = role;
	}

	public User(Integer userId, String login, String password, String firstName, String lastName, String email) {
		this(userId, login, password, firstName, lastName, email, true, Role.USER);
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserLoginDetails [loginId=" + loginId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", createdOn=" + createdOn + ", enabled=" + enabled + ", role=" + role + "]";
	}
}
