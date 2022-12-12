package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.entity.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, String>{

	@Query("""
			SELECT sal.employeeNumber, CONCAT(sal.firstName , ' ', sal.lastName), sal.level, CONCAT(sup.firstName, ' ', sup.lastName)
			FROM Salesman AS sal
				LEFT JOIN sal.superior AS sup
			WHERE sal.employeeNumber LIKE %:employeeNumber%
				AND CONCAT(sal.firstName, ' ', sal.lastName) LIKE %:name%
				AND sal.level LIKE %:employeeLevel%
				AND (:superiorName = '' OR CONCAT(sup.firstName, ' ', sup.lastName) LIKE CONCAT('%',:superiorName,'%')) """)
	public Page<Object> findAll(@Param("employeeNumber") String employeeNumber,
								@Param("name") String name,
								@Param("employeeLevel") String employeeLevel,
								@Param("superiorName") String superiorName,
								Pageable pageable);
	
	@Query("""
			SELECT sal.employeeNumber, CONCAT(sal.firstName , ' ', sal.lastName)
			FROM Salesman AS sal
			ORDER By sal.firstName """)
	public List<Object> findAllOrderByFirstName();
	
	@Query("""
			SELECT COUNT(sal.employeeNumber)
			FROM Salesman AS sal
			WHERE sal.employeeNumber = :employeeNumber""")
	public Long count(@Param("employeeNumber") String employeeNumber);
	
	@Query("""
			SELECT COUNT(sal.employeeNumber)
			FROM Salesman AS sal
			WHERE sal.superiorEmployeeNumber = :superiorEmployeeNumber""")
	public Long countBySuperiorEmployeeNumber(@Param("superiorEmployeeNumber") String superiorEmployeeNumber);
	
	@Query("""
			SELECT sal.employeeNumber, CONCAT(sal.firstName , ' ', sal.lastName), sal.level, CONCAT(sup.firstName, ' ', sup.lastName)
			FROM Region AS reg
				INNER JOIN reg.salesmen AS sal
				LEFT JOIN sal.superior AS sup
			WHERE reg.id = :regionId
				AND sal.employeeNumber LIKE %:employeeNumber%
				AND CONCAT(sal.firstName, ' ', sal.lastName) LIKE %:name%
				AND sal.level LIKE %:employeeLevel%
				AND (:superiorName = '' OR CONCAT(sup.firstName, ' ', sup.lastName) LIKE CONCAT('%',:superiorName,'%'))""")
	public Page<Object> findAll(@Param("regionId") Long regionId,
			@Param("employeeNumber") String employeeNumber,
			@Param("name") String name, 
			@Param("employeeLevel") String employeeLevel,
			@Param("superiorName") String superiorName, 
			Pageable pageable);

	
	@Query("""
			SELECT COUNT(sal.employeeNumber)
			FROM Region AS reg
				INNER JOIN reg.salesmen AS sal
			WHERE reg.id = :regionId
				AND sal.employeeNumber = :employeeNumber """)
	public Long count(@Param("regionId") Long regionId, @Param("employeeNumber") String employeeNumber);
}