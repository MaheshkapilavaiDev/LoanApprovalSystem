package com.loanapprovalsystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.loanapprovalsystem.entity.LoanApplication;
import com.loanapprovalsystem.enums.LoanState;
import com.loanapprovalsystem.repository.LoanApplicationRepository;

import jakarta.transaction.Transactional;

public class RiskAssessmentService {
	
	@Autowired
	private LoanApplicationRepository loanRepo;
	
	@Autowired
	private RejectLoanService rejectLoan;
	
	@Autowired
	private LoanApplicationService loanApplicationService;;
	
	@Transactional
	public void riskAssessment(Long loanId) {

	    LoanApplication loan =
	            loanRepo.findById(loanId)
	            .orElseThrow();

	    if(loan.getState()
	       != LoanState.VERIFIED) {

	       throw new RuntimeException(
	             "Invalid State");
	    }

	    loan.setState(
	          LoanState.RISK_ASSESSMENT);

	    loanRepo.save(loan);

	    double riskScore =
	            calculateRisk(loan);

	    if(riskScore > 80) {

	    	rejectLoan.rejectLoan(
	             loanId,
	             "High Risk");

	    } else {

	    	loanApplicationService.approveLoan(
	             loanId);
	    }
	}
	
	private double calculateRisk(
	        LoanApplication loan) {

	    double ratio =
	       loan.getLoanAmount()
	       / loan.getAnnualIncome();

	    if(ratio > 2)
	        return 90;

	    if(ratio > 1)
	        return 70;

	    return 30;
	}
	
	

}
