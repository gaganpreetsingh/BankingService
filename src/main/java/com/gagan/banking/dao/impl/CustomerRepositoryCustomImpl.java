package com.gagan.banking.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.gagan.banking.constant.Constants;
import com.gagan.banking.dao.CustomerRepositoryCustom;
import com.gagan.banking.exception.ResourceNotFoundException;
import com.gagan.banking.model.Customer;

@Repository
public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepositoryCustomImpl.class);
	@PersistenceContext
	EntityManager entityManager;

	public Customer findCustomerByCustLogin(String custLogin) {
		try {
			LOGGER.debug("findCustomerByCustLogin -- Started");
			Customer customer = null;
			Object obj = entityManager.createNamedQuery("Customer.fintCustomerIDByLogin")
					.setParameter("custLogin", custLogin).getSingleResult();

			if (obj != null) {
				customer = (Customer) obj;
				LOGGER.debug("Customer Name " + customer.getCustFname());
			}
			LOGGER.debug("findCustomerByCustLogin -- Ended");
			return customer;
		} catch (EmptyResultDataAccessException | NoResultException npe) {
			throw new ResourceNotFoundException(Constants.RESOURCE_DOESN_T_EXIST);
		}
	}

}
