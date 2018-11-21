package com.banking.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banking.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * Using a Custom Query with parameters
	 */

	@Query("Select a FROM Account a WHERE a.user.id = :userId")
	List<Account> findAccounts(@Param("userId") int userId);

	/**
	 * Using a Method Query
	 */
	Account findById(Integer id);

}
