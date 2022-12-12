package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query("""
			SELECT cat.id, cat.name, cat.description
			FROM Category AS cat
			WHERE cat.name LIKE %:name%	""")
	public Page<Object> findAll(@Param("name") String name, Pageable pageable);
	
	@Query("""
			SELECT COUNT(cat.id)
			FROM Category AS cat
			WHERE cat.name = :name AND cat.id != :id""")
	public Long count(@Param("id") Long id, @Param("name") String name);
	
	@Query("""
			SELECT cat.id, cat.name
			FROM Category AS cat
			ORDER By cat.name """)
	public List<Object> findAllOrderByName();
}
