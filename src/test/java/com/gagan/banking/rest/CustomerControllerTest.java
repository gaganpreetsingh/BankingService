package com.gagan.banking.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.gagan.banking.config.ConfigProperties;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	ConfigProperties configProp;

	private String getBaseURL() {
		return "http://localhost:" + port;
	}

	String custLogin, txTypeCode;

	private String getAPIEndpoint() {
		return getBaseURL() + "/api/v1/customers/";
	}

	@Before
	public void initData() {
		custLogin = "GAGAN001";
		txTypeCode = "CR";
	}

	@Test
	public void test_customer_account_transactions_with_valid_cust_login() {
		RestAssured.baseURI = getAPIEndpoint();

		Response response = RestAssured.given()
				.get(custLogin + "/txs/txType/" + txTypeCode);
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.getString("result"));
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK.value());
	}

	@Test
	public void test_customer_account_transactions_with_invalid_cust_login() {
		RestAssured.baseURI = getAPIEndpoint();
		custLogin = "Invalid";
		Response response = RestAssured.given()
				.get(custLogin + "/txs/txType/" + txTypeCode);
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void test_calculate_interest_with_valid_cust_login() {
		RestAssured.baseURI = getAPIEndpoint();

		Response response = RestAssured.given()
				.get(custLogin + "/calculateInterest/");
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.getString("result"));
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK.value());
	}

	@Test
	public void test_calculate_interest_with_invalid_cust_login() {
		RestAssured.baseURI = getAPIEndpoint();
		custLogin = "InvalidCust";
		Response response = RestAssured.given()
				.get(custLogin + "/calculateInterest/");
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND.value());
	}
}