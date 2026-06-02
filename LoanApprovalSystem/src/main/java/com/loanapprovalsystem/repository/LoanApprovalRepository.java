package com.loanapprovalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loanapprovalsystem.entity.Approval;

@Repository
public interface LoanApprovalRepository extends JpaRepository<Approval, Long> {

}
