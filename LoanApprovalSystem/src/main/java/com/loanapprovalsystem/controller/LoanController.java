package com.loanapprovalsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanapprovalsystem.dto.LoanRequest;
import com.loanapprovalsystem.dto.RejectRequest;
import com.loanapprovalsystem.entity.LoanApplication;
import com.loanapprovalsystem.service.LoanApplicationService;
import com.loanapprovalsystem.service.LoanVerificationService;
import com.loanapprovalsystem.service.RejectLoanService;
import com.loanapprovalsystem.service.RiskAssessmentService;

@RestController
@RequestMapping("api/loans")
public class LoanController {

	@Autowired
	private LoanApplicationService loanService;

	@Autowired
	private LoanVerificationService verificationService;

	@Autowired
	private LoanApplicationService approvalService;

	@Autowired
	private RiskAssessmentService riskassessmentService;

	@Autowired
	private RejectLoanService rejectLoanService;

	@PostMapping("/submit")
	public ResponseEntity<LoanApplication> submitLoan(@RequestBody LoanRequest request,
			@RequestHeader("Idempotency-Key") String key) {
		LoanApplication loan = loanService.submitLoan(request, key);

		return ResponseEntity.ok(loan);
	}

	@PutMapping("/{loanId}/verify")
	public ResponseEntity<String> verifyLoan(@PathVariable Long loanId) {

		verificationService.verifyLoan(loanId);

		return ResponseEntity.ok("Loan verified successfully");

	}

	@PutMapping("/{id}/risk")
	public ResponseEntity<String> riskAssessment(@PathVariable("id") Long loanId) {

		riskassessmentService.riskAssessment(loanId);

		return ResponseEntity.ok("Risk assessment completed successfully");
	}

	@PutMapping("/{id}/approve")
	public ResponseEntity<String> approveLoan(@PathVariable Long id) {

		approvalService.approveLoan(id, 0);

		return ResponseEntity.ok("Loan approved successfully");
	}

	@PutMapping("/{id}/reject")
	public ResponseEntity<String> rejectLoan(@PathVariable("id") Long longId) {

		rejectLoanService.rejectLoan(longId, "Auto Rejected");

		return ResponseEntity.ok("Loan rejected successfully");

	}

	@GetMapping("/{id}")
	public ResponseEntity<LoanApplication> getLoan(@PathVariable Long id) {

		LoanApplication loan = loanService.getLoan(id);

		return ResponseEntity.ok(loan);
	}

}
