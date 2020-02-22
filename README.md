<b>Main class: </b>Run OperateBankAccountApplication.class file <br/>

Below SQL scripts would be picked by Spring boot for DB Schema creation and Initial Data Setup :<br/>
<b>schema.sql :</b> Refers to DB schema setup<br/>
<b>data.sql:</b> Refers to initial data setup<br/>
*******************************************************

<b>1. Access H2 DB:</b>
http://localhost:8080/h2-console/<br/>
<b>JDBC URL:</b> jdbc:h2:mem:bankdb<br/>
<b>Username:</b> gagan<br/>
<b>Password:</b> preet<br/>

<b>2. Swagger UI:</b>
http://localhost:8080/swagger-ui.html

<b>3. Access below endpoints:</b><br/>
<b>Account Services:</b><br/>
<b>Service 1 URL: (Fetch Customer Balance)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/checkBalance

<b>Service 2 URL: (Get Transactions)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/txType/CR<br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/txType/DR<br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/txType/ALL<br/>

<b>Service 3 URL: (Calculate Interest on given A/c)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/calculateInterest/<br/>
<br/>
<b>Customer Services:</b><br/>
<b>Service 4 URL: (Get Customer's all A/cs Transactions)</b><br/>
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/CR
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/DR
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/ALL
<br/>
<b>Service 5 URL: (Calculate Interest on Customer's all A/cs)</b><br/>
http://localhost:8080/api/v1/customers/GAGAN001/calculateInterest/<br/>
<br/>
