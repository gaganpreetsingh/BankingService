node {
    stage 'Checkout'
    git branch: 'without-spring-security', 
    url: 'https://github.com/gaganpreetsingh/BankingService.git'
    // Get the maven tool
    
    // ** NOTE: This 'M3' maven tool must be configured in the global configuration
    def mvnHome = tool 'M3'
     
    stage 'Build'
    sh "mvn clean install -DskipTests"
 
    stage 'Test'
    sh "${mvnHome}/bin/mvn test"
}
