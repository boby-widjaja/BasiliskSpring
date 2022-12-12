package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

	@Query("""
			SELECT del.id, del.companyName, del.phone, del.cost
			FROM Delivery AS del
			WHERE del.companyName LIKE %:companyName% """)
	public Page<Object> findAll(@Param("companyName") String companyName, Pageable pageable);
	
	@Query("""
			SELECT COUNT(del.id)
			FROM Delivery AS del
			WHERE (:id = 0l AND del.companyName = :companyName) OR 
				(del.id = :id AND del.companyName = :companyName) """)
	public Long count(@Param("id") Long id, @Param("companyName") String companyName);

	@Query("""
			SELECT del.id, del.companyName
			FROM Delivery AS del
			ORDER By del.companyName """)
	public List<Object> findAllOrderByCompanyName();
}
