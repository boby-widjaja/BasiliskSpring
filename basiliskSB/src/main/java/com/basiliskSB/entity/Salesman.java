package com.basiliskSB.entity;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Salesmen")
public class Salesman {
	@Id
	@Column(name="EmployeeNumber")
	private String employeeNumber;
	
	@Column(name="FirstName")
	private String firstName;
	
	@Column(name="LastName")
	private String lastName;
	
	@Column(name="Level")
	private String level;
	
	@Column(name="BirthDate")
	private LocalDate birthDate;
	
	@Column(name="HiredDate")
	private LocalDate hiredDate;
	
	@Column(name="Address")
	private String address;
	
	@Column(name="City")
	private String city;
	
	@Column(name="Phone")
	private String phone;
	
	@Column(name="SuperiorEmployeeNumber")
	private String superiorEmployeeNumber;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="SuperiorEmployeeNumber", insertable=false, updatable=false)
	private Salesman superior;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name="SalesmenRegions", 
		joinColumns=@JoinColumn(name="SalesmanEmployeeNumber"),
		inverseJoinColumns=@JoinColumn(name="RegionId"))
	private List<Region> regions;

	public Salesman(String employeeNumber, String firstName, String lastName, String level, LocalDate birthDate, LocalDate hiredDate, String address,
			String city, String phone, String superiorEmployeeNumber) {
		this.employeeNumber = employeeNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.level = level;
		this.birthDate = birthDate;
		this.hiredDate = hiredDate;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.superiorEmployeeNumber = superiorEmployeeNumber;
	}
}
