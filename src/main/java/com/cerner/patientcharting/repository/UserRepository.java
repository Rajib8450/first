package com.cerner.patientcharting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cerner.patientcharting.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	/**
	 * Queries the database for boolean count of a user with a given userName
	 * @param userName
	 * @return Boolean
	 */
	@Query("select new java.lang.Boolean(count(*) > 0) from User where userName = ?1")
	Boolean exsistByUserName(String userName);
	/**
	 * Queries the database for user Details with a given userName
	 * @param userName
	 * @return user
	 */
	@Query("Select u from User u where u.userName= ?1")
	User findByUserName(String userName);
}

