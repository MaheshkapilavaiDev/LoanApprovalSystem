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
public class RiskAssessmentService {

    @Autowired
    private LoanApplicationRepository loanRepo;

    @Autowired
    private LoanApprovalRepository approvalRepo;

    @Autowired
    private RejectLoanService rejectLoan;

    @Autowired
    private AuditService auditService;

    @Transactional
    public void riskAssessment(Long loanId) {

        LoanApplication loan = loanRepo.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan Not Found"));

        if (loan.getState() != LoanState.VERIFIED) {

            throw new RuntimeException(
                    "Loan must be VERIFIED first");
        }

        loan.setState(LoanState.RISK_ASSESSMENT);

        loanRepo.save(loan);

        auditService.createAudit(
                loanId,
                "VERIFIED",
                "RISK_ASSESSMENT");

        double riskScore = calculateRisk(loan);

        if (riskScore > 80) {

            rejectLoan.rejectLoan(
                    loanId,
                    "High Risk Customer");

        } else if (riskScore < 40) {

            approveLoan(
                    loanId,
                    riskScore);

        } else {

            loan.setState(LoanState.MANUAL_REVIEW);

            loanRepo.save(loan);

            auditService.createAudit(
                    loanId,
                    "RISK_ASSESSMENT",
                    "MANUAL_REVIEW");
        }
    }

    private double calculateRisk(
            LoanApplication loan) {

        double ratio =
                loan.getLoanAmount()
                / loan.getAnnualIncome();

        if (ratio > 2) {
            return 90;
        }

        if (ratio > 1) {
            return 70;
        }

        return 30;
    }

    @Transactional
    public Approval approveLoan(
            Long loanId,
            double riskScore) {

        LoanApplication loan = loanRepo.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan Not Found"));

        if (loan.getState() != LoanState.RISK_ASSESSMENT) {

            throw new RuntimeException(
                    "Loan must be in RISK_ASSESSMENT state");
        }

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
}