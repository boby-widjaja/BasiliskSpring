package com.basiliskSB.entity;
import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Deliveries")
public class Delivery {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	
	@Column(name="CompanyName")
	private String companyName;
	
	@Column(name="Phone")
	private String phone;
	
	@Column(name="Cost")
	private Double cost;
}
