package com.cerner.patientcharting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cerner.patientcharting.model.PatientDetails;
@Repository
public interface PatientDetailsRepository extends JpaRepository<PatientDetails, String>{
	List<PatientDetails> findByName(String name);
}
