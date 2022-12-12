package com.basiliskSB.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
	
	@Query("""
			SELECT COUNT(acc.username) 
			FROM Account AS acc
			WHERE acc.username = :username	""")
	public Long count(@Param("username") String username);
}
