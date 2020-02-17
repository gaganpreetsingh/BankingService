package com.gagan.banking.dao.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gagan.banking.dao.AccountRepositoryCustom;
import com.gagan.banking.model.CustAccount;

@Repository
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Optional<CustAccount> findByAccNo(String accNo) {
		Query query = entityManager.createNamedQuery("CustAccount.fintAccByNum", CustAccount.class)
				.setParameter("accNo", accNo);
		return query.getResultList().stream().findFirst();
	}
}
