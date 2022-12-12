package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long>{
	@Query("""
			SELECT reg.id, reg.city, reg.remark
			FROM Region AS reg
			WHERE reg.city LIKE %:city% """)
	public Page<Object> findAll(@Param("city") String city, Pageable pageable);
	
	@Query("""
			SELECT reg.id, reg.city
			FROM Region AS reg
			ORDER By reg.city """)
	public List<Object> findAllOrderByCity();
	
	@Query("""
			SELECT reg.id, reg.city, reg.remark
			FROM Salesman AS sal
				INNER JOIN sal.regions AS reg
			WHERE sal.employeeNumber = :employeeNumber AND reg.city LIKE %:city% """)
	public Page<Object> findAll(@Param("employeeNumber") String employeeNumber, @Param("city") String city, Pageable pageable);
	
	@Query("""
			SELECT COUNT(reg.id)
			FROM Salesman AS sal
				INNER JOIN sal.regions AS reg
			WHERE sal.employeeNumber = :employeeNumber AND reg.id = :regionId """)
	public Long count(@Param("employeeNumber") String employeeNumber, @Param("regionId") Long regionId);
}
