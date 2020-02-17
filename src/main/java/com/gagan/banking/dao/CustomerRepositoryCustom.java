package com.gagan.banking.dao;

import com.gagan.banking.model.Customer;

public interface CustomerRepositoryCustom {

	Customer findCustomerByCustLogin(String custLogin);

}
