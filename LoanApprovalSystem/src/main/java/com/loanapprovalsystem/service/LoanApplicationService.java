package com.loanapprovalsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.loanapprovalsystem.dto.ApprovalRequest;
import com.loanapprovalsystem.dto.LoanRequest;
import com.loanapprovalsystem.entity.Approval;
import com.loanapprovalsystem.entity.LoanApplication;
import com.loanapprovalsystem.enums.LoanState;
import com.loanapprovalsystem.repository.LoanApplicationRepository;
import com.loanapprovalsystem.repository.LoanApprovalRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanApplicationService {

	@Autowired
	private LoanApplicationRepository loanRepo;

	@Autowired
	private LoanApprovalRepository approvalRepo;

	@Autowired
	private AuditService auditService;
	

	@Transactional
	public LoanApplication submitLoan(LoanRequest request, String idempotencyKey) {

		Optional<LoanApplication> existing = loanRepo.findByIdempotencyKey(idempotencyKey);

		if (existing.isPresent()) {
			return existing.get();
		}

		LoanApplication loan = new LoanApplication();

		loan.setApplicantName(request.getApplicantName());

		loan.setEmail(request.getEmail());

		loan.setLoanAmount(request.getLoanAmount());

		loan.setAnnualIncome(request.getAnnualIncome());

		loan.setState(LoanState.SUBMITTED);

		loan.setIdempotencyKey(idempotencyKey);

		loanRepo.save(loan);

		return loan;
	}

	/*@Transactional
	public Approval approveLoan( ApprovalRequest request,Long loanId) {

		LoanApplication loan = loanRepo.findById(loanId).orElseThrow(()-> new RuntimeException("Loan Not Found"));

		loan.setState(LoanState.APPROVED);


		Approval approval = new Approval();
		
		approval.setDecision(request.getDecision());
		approval.setReason(request.getReason());
		approval.setRiskScore(request.getRiskScore());
		approval.setLoanApplication(loan);

		loanRepo.save(loan);

		auditService.createAudit(loanId, "VERIFIED", "APPROVED");
		
		return approvalRepo.save(approval);
	}*/
	@Transactional
	public Approval approveLoan(
	        Long loanId,
	        double riskScore) {

	    LoanApplication loan = loanRepo.findById(loanId)
	            .orElseThrow(() ->
	                    new RuntimeException("Loan Not Found"));

	    loan.setState(LoanState.APPROVED);

	    Approval approval = new Approval();

	    approval.setDecision("APPROVED");
	    approval.setReason("Auto Approved");
	    approval.setRiskScore(riskScore);
	    approval.setLoanApplication(loan);

	    loanRepo.save(loan);

	    auditService.createAudit(
	            loanId,
	            "RISK_ASSESSMENT",
	            "APPROVED");

	    return approvalRepo.save(approval);
	}
	public LoanApplication getLoan(Long id) {

        return loanRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found with id: " + id));
    }
}
