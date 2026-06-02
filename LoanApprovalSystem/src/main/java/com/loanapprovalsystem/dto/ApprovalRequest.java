package com.loanapprovalsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApprovalRequest {

	@NotBlank(message = "Decision is required")
	private String decision;

	private String reason;

	@NotNull(message = "Risk score is required")
	private Double riskScore;

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Double getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(Double riskScore) {
		this.riskScore = riskScore;
	}
}
