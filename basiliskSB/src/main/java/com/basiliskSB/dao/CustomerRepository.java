package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query("""
			SELECT cus.id, cus.companyName, cus.contactPerson, cus.address, cus.city
			FROM Customer AS cus
			WHERE cus.companyName LIKE %:companyName% AND cus.contactPerson LIKE %:contactPerson% AND cus.deleteDate = NULL """)
	public Page<Object> findAll(@Param("companyName") String companyName,
								@Param("contactPerson") String contactPerson, Pageable pageable);
	
	@Query("""
			SELECT cus.id, cus.companyName
			FROM Customer AS cus
			WHERE cus.deleteDate = NULL
			ORDER By cus.companyName """)
	public List<Object> findAllOrderByCompanyName();
}
