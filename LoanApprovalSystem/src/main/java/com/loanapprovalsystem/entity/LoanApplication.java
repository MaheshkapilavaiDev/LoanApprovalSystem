package com.loanapprovalsystem.entity;

import com.loanapprovalsystem.enums.LoanState;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "loan_application")
public class LoanApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String applicantName;

	private String email;

	private Double loanAmount;

	private Double annualIncome;

	@Enumerated(EnumType.STRING)
	private LoanState state;

	private String idempotencyKey;

	@Version
	private Long version;

	public LoanApplication() {
	}

	public LoanApplication(Long id, String applicantName, String email, Double loanAmount, Double annualIncome,
			LoanState state, String idempotencyKey, Long version) {
		super();
		this.id = id;
		this.applicantName = applicantName;
		this.email = email;
		this.loanAmount = loanAmount;
		this.annualIncome = annualIncome;
		this.state = state;
		this.idempotencyKey = idempotencyKey;
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public LoanState getState() {
		return state;
	}

	public void setState(LoanState state) {
		this.state = state;
	}

	public String getIdempotencyKey() {
		return idempotencyKey;
	}

	public void setIdempotencyKey(String idempotencyKey) {
		this.idempotencyKey = idempotencyKey;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
