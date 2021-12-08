package com.cerner.patientcharting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cerner.patientcharting.model.Patient;
@Repository
public interface PatientRepository extends JpaRepository<Patient, String>{
	/**
	 * Queries the database for boolean count of a patient with a given mrnNo
	 * @param mrnNo
	 * @return Boolean
	 */
	@Query("select new java.lang.Boolean(count(*) > 0) from Patient where mrnNo = ?1")
	Boolean exsistByPatient(String mrnNo);
}
