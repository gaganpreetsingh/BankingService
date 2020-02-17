<b>Main class: </b>Run OperateBankAccountApplication.class file <br/>

Below SQL scripts would be picked by Spring boot for DB Schema creation and Initial Data Setup :<br/>
<b>schema.sql :</b> Refers to DB schema setup<br/>
<b>data.sql:</b> Refers to initial data setup<br/>
***************************************************
<b>1. Access H2 DB: </b><br/>
http://localhost:8080/h2-console/<br/>
<b>JDBC URL:</b> jdbc:h2:mem:bankdb<br/>
<b>Username:</b> gagan<br/>
<b>Password:</b> preet<br/>

<b>2. Swagger UI:</b>
http://localhost:8080/swagger-ui.html
*****************************************
<b>3. Send POST request to generate access token with Basic Auth credentials mentioned below at point 3.1: </b><br/>
http://localhost:8080/oauth/token?grant_type=password&username=super.user&password=password <br/>
<b>3.1 Basic Auth:</b><br/>
	<b>Username:</b> trusted-client<br/>
	<b>Password:</b> e5e9fa1ba31ecd1ae84f75caaa474f3a663f05f4 (SHA-128)<br/>
<b>Response:</b><br/>
access token: 58426cb8-011c-4d5a-ae78-95c7bac85bcc<br/>
<b>Note:</b> Only ADMIN and MANAGER roles can access APIs<br/><br/>
<b>List of Users:</b><br/>
 a) super.user (ADMIN, MANAGER) <br/>
 b) bob.rob (CLERK) <br/>
 c) smeng107 (BANKER)<br/>
 <br/>
4. Copy above access token to get authorize and Add Authorization Header : Bearer {access-token} to access all APIs:<br/>
<b>Service 1 URL: (Fetch Customer Balance)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/checkBalance

<b>Service 2 URL: (Get Transactions)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/accActType/CR
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/accActType/DR
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/accActType/ALL
<br/><br/>
<b>Service 3 URL: (Calculate Interest on given A/c)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/calculateInterest/<br/>
<br/>
<b>Service 4 URL: (Get Customer's all accounts Transactions)</b><br/>
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/CR
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/DR
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/ALL
<br/>
<b>Service 5 URL: (Calculate Interest on Customer's all A/cs)</b><br/>
http://localhost:8080/api/v1/customers/GAGAN001/calculateInterest/<br/>
<br/>
