package com.tinusj.assignment.repository;

import com.tinusj.assignment.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
