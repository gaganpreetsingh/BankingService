node {
    stage 'Checkout'
    git branch: 'without-spring-security', 
    url: 'https://github.com/gaganpreetsingh/BankingService.git'
    // Get the maven tool
     def mvnHome = tool 'M3'
    environment : {
        PATH = "${mvnHome}/bin:$PATH"
    }
    stage 'Build'
    sh "${mvnHome}/bin/mvn clean install -DskipTests"
 
    stage 'Test'
    sh "${mvnHome}/bin/mvn -Dtest='AccountServiceTest,CustomerServiceTest,AccountRepositoryTest,CustomerRepositoryTest' test"
}
