package com.loanapprovalsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Transactional
	public void approveLoan(Long loanId) {

		LoanApplication loan = loanRepo.findById(loanId).orElseThrow();

		loan.setState(LoanState.APPROVED);

		loanRepo.save(loan);

		Approval approval = new Approval();
		approval.setDecision("APPROVED");

		approvalRepo.save(approval);

		auditService.createAudit(loanId, "VERIFIED", "APPROVED");
	}
	
	

}
