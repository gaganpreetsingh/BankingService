package com.gagan.banking.rest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

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

	private String authUserAccessToken;

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

	@Before
	public void obtainAccessToken() {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("username", "super.user");
		params.put("password", "password");
		Response response = RestAssured.given().auth().preemptive().basic("trusted-client", configProp.getSecretKey())
				.and().with().params(params).when().post(getBaseURL() + "/oauth/token");

		authUserAccessToken = response.jsonPath().getString("access_token");
	}

	@Test
	public void test_apis_without_access_token() {
		Response response = RestAssured.get(getAPIEndpoint());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	}

	@Test
	public void test_customer_account_transactions_with_valid_cust_login() {
		RestAssured.baseURI = getAPIEndpoint();

		Response response = RestAssured.given().header("Authorization", "Bearer " + authUserAccessToken)
				.get(custLogin + "/txs/txType/" + txTypeCode);
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.getString("result"));
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK.value());
	}

	@Test
	public void test_customer_account_transactions_with_invalid_cust_login() {
		RestAssured.baseURI = getAPIEndpoint();
		custLogin = "Invalid";
		Response response = RestAssured.given().header("Authorization", "Bearer " + authUserAccessToken)
				.get(custLogin + "/txs/txType/" + txTypeCode);
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void test_calculate_interest_with_valid_cust_login() {
		RestAssured.baseURI = getAPIEndpoint();

		Response response = RestAssured.given().header("Authorization", "Bearer " + authUserAccessToken)
				.get(custLogin + "/calculateInterest/");
		JsonPath jsonPath = response.jsonPath();
		Assert.assertNotNull(jsonPath.getString("result"));
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK.value());
	}

	@Test
	public void test_calculate_interest_with_invalid_cust_login() {
		RestAssured.baseURI = getAPIEndpoint();
		custLogin = "InvalidCust";
		Response response = RestAssured.given().header("Authorization", "Bearer " + authUserAccessToken)
				.get(custLogin + "/calculateInterest/");
		Assert.assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND.value());
	}
}