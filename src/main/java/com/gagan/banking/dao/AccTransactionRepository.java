package com.gagan.banking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gagan.banking.model.AccTransaction;

public interface AccTransactionRepository extends JpaRepository<AccTransaction, Long>, AccountRepositoryCustom {

}
