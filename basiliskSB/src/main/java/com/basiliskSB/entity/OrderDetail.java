package com.basiliskSB.entity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="OrderDetails")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	
	@Column(name="InvoiceNumber")
	private String invoiceNumber;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="InvoiceNumber", insertable=false, updatable=false)
	private Order order;
	
	@Column(name="ProductId")
	private Long productId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ProductId", insertable=false, updatable=false)
	private Product product;
	
	@Column(name="UnitPrice")
	private Double unitPrice;
	
	@Column(name="Quantity")
	private Integer quantity;
	
	@Column(name="Discount")
	private Double discount;

	public OrderDetail(Long id, String invoiceNumber, Long productId, Double unitPrice, Integer quantity, Double discount) {
		this.id = id;
		this.invoiceNumber = invoiceNumber;
		this.productId = productId;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.discount = discount;
	}
}
