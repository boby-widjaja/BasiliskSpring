package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("""
			SELECT pro.id, pro.name, sup.companyName, cat.name, pro.price, pro.imagePath
			FROM Product AS pro
				INNER JOIN pro.category AS cat
				LEFT JOIN pro.supplier AS sup
			WHERE pro.name LIKE %:name%
				AND (:categoryId IS NULL OR pro.categoryId = :categoryId)
				AND (:supplierId IS NULL OR pro.supplierId = :supplierId) """)
	public Page<Object> findAll(@Param("name") String name,
								@Param("categoryId") Long categoryId, @Param("supplierId") Long supplierId, Pageable pageable);

	@Query("""
			SELECT COUNT(pro.id)
			FROM Product AS pro
			WHERE pro.categoryId = :categoryId """)
	public Long countByCategoryId(@Param("categoryId") Long categoryId);
	
	@Query("""
			SELECT pro.id, pro.name
			FROM Product AS pro
			ORDER By pro.name """)
	public List<Object> findAllOrderByName();
}
