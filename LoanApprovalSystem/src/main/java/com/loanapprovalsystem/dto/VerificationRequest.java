package com.loanapprovalsystem.dto;

import jakarta.validation.constraints.NotNull;

public class VerificationRequest {

	@NotNull(message = "Verification status is required")
	private Boolean verified;

	private String remarks;

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}