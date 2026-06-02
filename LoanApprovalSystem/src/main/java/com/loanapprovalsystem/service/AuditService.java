package com.loanapprovalsystem.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanapprovalsystem.entity.AuditLog;
import com.loanapprovalsystem.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {

	@Autowired
    private  AuditLogRepository auditRepo;

    public void createAudit(Long loanId,
                            String oldStatus,
                            String newStatus) {

        AuditLog audit = new AuditLog();

        audit.setLoanId(loanId);
        audit.setOldStatus(oldStatus);
        audit.setNewStatus(newStatus);
        audit.setTimestamp(LocalDateTime.now());

        auditRepo.save(audit);
    }
}
