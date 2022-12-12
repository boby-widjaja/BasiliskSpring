package com.basiliskSB.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.entity.OrderDetail;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	@Query("""
			SELECT ordet.id, prod.name, ordet.unitPrice, ordet.quantity, ordet.discount 
			FROM OrderDetail AS ordet
				INNER JOIN ordet.product AS prod
			WHERE ordet.invoiceNumber = :invoiceNumber""")
	public Page<Object> findAll(@Param("invoiceNumber") String invoiceNumber, Pageable pageable);
	
	@Query("""
			SELECT COUNT(ordet.id) 
			FROM OrderDetail AS ordet
			WHERE ordet.productId = :productId""")
	public Long countByProductId(@Param("productId") Long productId);
}
