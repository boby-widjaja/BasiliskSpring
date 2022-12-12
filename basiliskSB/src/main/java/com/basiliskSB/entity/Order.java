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
@Table(name="Orders")
public class Order {
	@Id
	@Column(name="InvoiceNumber")
	private String invoiceNumber;
	
	@Column(name="CustomerId")
	private Long customerId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="CustomerId", insertable=false, updatable=false)
	private Customer customer;
	
	@Column(name="SalesEmployeeNumber")
	private String salesEmployeeNumber;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="SalesEmployeeNumber", insertable=false, updatable=false)
	private Salesman salesman;
	
	@Column(name="OrderDate")
	private LocalDate orderDate;
	
	@Column(name="ShippedDate")
	private LocalDate shippedDate;
	
	@Column(name="DueDate")
	private LocalDate dueDate;
	
	@Column(name="DeliveryId")
	private Long deliveryId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="DeliveryId", insertable=false, updatable=false)
	private Delivery delivery;
	
	@Column(name="DeliveryCost")
	private Double deliveryCost;
	
	@Column(name="DestinationAddress")
	private String destinationAddress;
	
	@Column(name="DestinationCity")
	private String destinationCity;
	
	@Column(name="DestinationPostalCode")
	private String destinationPostalCode;

	@JsonIgnore
	@OneToMany(mappedBy="order", cascade={CascadeType.REMOVE})
	private List<OrderDetail> orderDetails;

	public Order(String invoiceNumber, Long customerId, String salesEmployeeNumber, LocalDate orderDate, LocalDate shippedDate, LocalDate dueDate,
			Long deliveryId, Double deliveryCost, String destinationAddress, String destinationCity, String destinationPostalCode) {
		this.invoiceNumber = invoiceNumber;
		this.customerId = customerId;
		this.salesEmployeeNumber = salesEmployeeNumber;
		this.orderDate = orderDate;
		this.shippedDate = shippedDate;
		this.dueDate = dueDate;
		this.deliveryId = deliveryId;
		this.deliveryCost = deliveryCost;
		this.destinationAddress = destinationAddress;
		this.destinationCity = destinationCity;
		this.destinationPostalCode = destinationPostalCode;
	}
}
