package com.loanapprovalsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanapprovalsystem.dto.LoanRequest;
import com.loanapprovalsystem.entity.LoanApplication;
import com.loanapprovalsystem.service.LoanApplicationService;
import com.loanapprovalsystem.service.LoanVerificationService;

@RestController
@RequestMapping("api/loans")
public class LoanController {

	@Autowired
	private LoanApplicationService loanService;

	@Autowired
	private LoanVerificationService verificationService;

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
}
