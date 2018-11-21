package com.banking.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.enabled = 1")
	List<User> findAllActiveUsers();

	@Query("SELECT u FROM User u WHERE u.id = :id")
	User findOne(@Param("id") int id);

	@Query("SELECT u FROM User u WHERE u.loginId = :loginId")
	User findOne(@Param("loginId") String loginId);

}
