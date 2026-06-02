package com.loanapprovalsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loanapprovalsystem.entity.LoanApplication;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
	
	 Optional<LoanApplication>
     findByIdempotencyKey(String key);

}
