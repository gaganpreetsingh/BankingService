node {
    stage 'Checkout'
    git branch: 'without-spring-security', 
    url: 'https://github.com/gaganpreetsingh/BankingService.git'
    // Get the maven tool
     def mvnHome = tool 'M3'
    environment : {
        PATH = "${mvnHome}:$PATH"
    }
    stage 'Build'
    sh "${mvnHome}/mvn clean install -DskipTests"
 
    stage 'Test'
    sh "${mvnHome}/bin/mvn test"
}
