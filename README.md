<b>1. H2 DB URI</b>
http://localhost:8080/h2-console/

<b>2. Swagger UI:</b>
http://localhost:8080/swagger-ui.html

<b>3. Access below endpoints
<b>Service 1 URL: (Fetch Customer Balance)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/checkBalance

<b>Service 2 URL: (Get Transactions)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/txType/CR
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/txType/DR
http://localhost:8080/api/v1/accounts/ASQ-33171393372/txs/txType/ALL
<br/><br/>
<b>Service 3 URL: (Calculate Interest on Customer's all accounts on daily basis)</b><br/>
http://localhost:8080/api/v1/accounts/ASQ-33171393372/calculateInterest/<br/>
<br/>
<b>Service 4 URL: (Get Customer's all accounts Transactions)</b><br/>
http://localhost:8080/api/v1/customers/GAGAN001/txs/txType/CR
http://localhost:8080/api/v1/customers/GAGAN001/txs/txType/DR
http://localhost:8080/api/v1/customers/GAGAN001/txs/txType/ALL
<br/>
<b>Service 5 URL: (Get Customer's all account Transactions)</b><br/>
http://localhost:8080/api/v1/customers/GAGAN001/calculateInterest/<br/>
<br/>
<b>Main class: </b>Run OperateBankAccountApplication.class file <br/>

Below SQL scripts would be picked by Spring boot for DB Schema creation and Initial Data Setup :<br/>
<b>schema.sql :</b> Refers to DB schema setup<br/>
<b>data.sql:</b> Refers to initial data setup<br/>

http://localhost:8080/api/v1/accounts/ASQ-33171393372/calculateInterest/<br/>
<br/>
<b>Service 4 URL: (Get Customer's all accounts Transactions)</b><br/>
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/CR
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/DR
http://localhost:8080/api/v1/customers/GAGAN001/txs/accActType/ALL
<br/>
<b>Service 5 URL: (Get Customer's all account Transactions)</b><br/>
http://localhost:8080/api/v1/customers/GAGAN001/calculateInterest/<br/>
<br/>
<b>Main class: </b>Run OperateBankAccountApplication.class file <br/>

Below SQL scripts would be picked by Spring boot for DB Schema creation and Initial Data Setup :<br/>
<b>schema.sql :</b> Refers to DB schema setup<br/>
<b>data.sql:</b> Refers to initial data setup<br/>
