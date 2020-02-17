package com.gagan.banking.rest;

import static org.junit.Assert.assertEquals;

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
public class AccountControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	ConfigProperties configProp;
	
	private String getBaseURL() {
		return "http://localhost:" + port;
	}

	String accNo, txTypeCode;

	private String getAPIEndpoint() {
		return getBaseURL() + "/api/v1/accounts/";
	}

	@Before
	public void initData() {
		accNo = "ASQ-34571393372";
		txTypeCode = "CR";
	}

	@Test
	public void test_get_balance_with_valid_acc_no() {
		RestAssured.baseURI = getAPIEndpoint();

		Response response = RestAssured.given()
				.get(accNo + "/checkBalance");
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.getDouble("result"));
	}

	@Test
	public void test_get_balance_with_invalid_acc_no() {
		RestAssured.baseURI = getAPIEndpoint();
		accNo = "1212";
		Response response = RestAssured.given()
				.get(accNo + "/checkBalance");
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void test_get_balance_with_null_acc_no() {
		RestAssured.baseURI = getAPIEndpoint();
		accNo = null;
		Response response = RestAssured.given()
				.get(accNo + "/checkBalance");
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void test_account_transactions_with_valid_acc_no() {
		RestAssured.baseURI = getAPIEndpoint();

		Response response = RestAssured.given()
				.get(accNo + "/txs/txType/" + txTypeCode);
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.getString("result"));
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK.value());
	}

	@Test
	public void test_account_transactions_with_invalid_acc_no() {
		RestAssured.baseURI = getAPIEndpoint();
		accNo = "Invalid";
		Response response = RestAssured.given()
				.get(accNo + "/txs/txType/" + txTypeCode);
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void test_calculate_interest_with_valid_acc_no() {
		RestAssured.baseURI = getAPIEndpoint();

		Response response = RestAssured.given()
				.get(accNo + "/calculateInterest/");
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.getString("result"));
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK.value());
	}

	@Test
	public void test_calculate_interest_with_invalid_acc_no() {
		RestAssured.baseURI = getAPIEndpoint();
		accNo = "InvalidAcc";
		Response response = RestAssured.given()
				.get(accNo + "/calculateInterest/");
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND.value());
	}
}