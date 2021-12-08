package com.cerner.patientcharting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="patient")
public class Patient {
	@Id
	@NotBlank
	@Column(unique=true)
	private String patientId;
	@NotBlank
	@Column(unique=true)
	private String mrnNo;
	@OneToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="patientId",referencedColumnName = "id",insertable = false,updatable = false)
	private PatientDetails patientDetails;

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public void setMrnNo(String mrnNo) {
		this.mrnNo = mrnNo;
	}
}
