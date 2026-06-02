package com.loanapprovalsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanapprovalsystem.entity.LoanApplication;
import com.loanapprovalsystem.enums.LoanState;
import com.loanapprovalsystem.repository.LoanApplicationRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanVerificationService {
	
	@Autowired
	private LoanApplicationRepository loanRepo;
	
	@Autowired
	private AuditService aditService;
	
	@Transactional
	public void verifyLoan(Long loanId) {

	    LoanApplication loan =
	        loanRepo.findById(loanId)
	            .orElseThrow();

	    if(loan.getState()
	        != LoanState.SUBMITTED) {

	        throw new RuntimeException(
	        		"Loan can only be verified when status is SUBMITTED");
	    }

	    loan.setState(
	          LoanState.VERIFIED);

	    loanRepo.save(loan);

	    aditService.createAudit(
	      loanId,
	      "SUBMITTED",
	      "VERIFIED");
	}

}
