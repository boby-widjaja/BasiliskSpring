package com.basiliskSB.entity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
@Entity
@Table(name="Products")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="SupplierId")
	private Long supplierId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SupplierId", insertable=false, updatable=false)
	private Supplier supplier;
	
	@Column(name="CategoryId")
	private Long categoryId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CategoryId", insertable=false, updatable=false)
	private Category category;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Price")
	private Double price;
	
	@Column(name="Stock")
	private Integer stock;
	
	@Column(name="OnOrder")
	private Integer onOrder;
	
	@Column(name="Discontinue")
	private Boolean discontinue;

	@Column(name="ImagePath")
	private String imagePath;

	public Product(Long id, String name, Long supplierId, Long categoryId, String description, Double price,
			Integer stock, Integer onOrder, Boolean discontinue, String imagePath) {
		this.id = id;
		this.name = name;
		this.supplierId = supplierId;
		this.categoryId = categoryId;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.onOrder = onOrder;
		this.discontinue = discontinue;
		this.imagePath = imagePath;
	}
}
