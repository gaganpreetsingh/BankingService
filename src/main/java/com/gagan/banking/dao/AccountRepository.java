package com.gagan.banking.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gagan.banking.model.CustAccount;

public interface AccountRepository extends JpaRepository<CustAccount, Long>, AccountRepositoryCustom {

}
