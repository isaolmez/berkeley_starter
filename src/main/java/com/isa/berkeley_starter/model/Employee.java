package com.isa.berkeley_starter.model;

import java.io.Serializable;

public class Employee implements Serializable {
	private String name;
	private String email;
	private String department;
	
	public Employee(String Name, String Email, String Department) {
		this.name = Name;
		this.email = Email;
		this.department = Department;
	}

	public Employee() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [Name=" + name + ", Email=" + email + ", Department=" + department + "]";
	}
}