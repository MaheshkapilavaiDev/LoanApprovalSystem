package com.loanapprovalsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanapprovalsystem.entity.Approval;
import com.loanapprovalsystem.entity.LoanApplication;
import com.loanapprovalsystem.enums.LoanState;
import com.loanapprovalsystem.repository.LoanApplicationRepository;
import com.loanapprovalsystem.repository.LoanApprovalRepository;

import jakarta.transaction.Transactional;

@Service
public class RejectLoanService {
	
	
	@Autowired
	private LoanApplicationRepository loanRepo;
	
	@Autowired
	private LoanApprovalRepository approvalRepo;
	
	@Transactional
	public void rejectLoan(
	      Long loanId,
	      String reason) {

	    LoanApplication loan =
	            loanRepo.findById(loanId)
	            .orElseThrow();

	    loan.setState(
	         LoanState.REJECTED);

	    loanRepo.save(loan);

	    Approval approval =
	            new Approval();

	    approval.setDecision(
	          "REJECTED");

	    approval.setReason(
	          reason);

	    approval.setLoanApplication(
	          loan);

	    approvalRepo.save(
	          approval);
	}

}
