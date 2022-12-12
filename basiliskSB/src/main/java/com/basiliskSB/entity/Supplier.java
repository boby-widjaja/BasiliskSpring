package com.basiliskSB.entity;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Suppliers")
public class Supplier {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	
	@Column(name="CompanyName")	
	private String companyName;
	
	@Column(name="ContactPerson")	
	private String contactPerson;
	
	@Column(name="JobTitle")
	private String jobTitle;
	
	@Column(name="Address")
	private String address;
	
	@Column(name="City")
	private String city;
	
	@Column(name="Phone")
	private String phone;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="DeleteDate")
	private LocalDateTime deleteDate;

	public Supplier(Long id, String companyName, String contactPerson, String jobTitle, String address, String city, String phone, String email) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.contactPerson = contactPerson;
		this.jobTitle = jobTitle;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.email = email;
	}
}
