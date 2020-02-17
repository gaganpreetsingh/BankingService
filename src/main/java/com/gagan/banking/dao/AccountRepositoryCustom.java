package com.gagan.banking.dao;

import java.util.Optional;

import com.gagan.banking.model.CustAccount;

public interface AccountRepositoryCustom {

	Optional<CustAccount> findByAccNo(String accNo);
}
