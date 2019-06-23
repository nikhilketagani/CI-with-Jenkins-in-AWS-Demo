node {
    try {
      def mvnHome
     stage('Preparation') { // for display purposes
       //Get some code from a GitHub repository
      git 'https://github.com/nikhilketagani/CI-with-Jenkins-in-AWS-Demo.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'Maven361'
   }   
   stage('Unit Test') {
       // Run the maven build
       withEnv(["MVN_HOME=$mvnHome"]) {
          if (isUnix()) {
             sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean test' ;
             junit '**/target/surefire-reports/*.xml' ;
          } else {
             bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean test/)
               junit '**/target/surefire-reports/*.xml'
          }
       }
   }
   stage('SonarQube analysis') {
     // requires SonarQube Scanner 2.8+
     def scannerHome = tool 'sonarqube-scanner';
     withSonarQubeEnv('sonarqube-server') {
       sh "${scannerHome}/bin/sonar-scanner -Dsonar.host.url=http://34.93.135.253:9000/ -Dsonar.login=6c64b9c6e7a0c8449cdea0acbe023a47a4d1bd2d -Dsonar.projectVersion=1.0 -Dsonar.projectKey=PROJECT -Dsonar.sourceEncoding=UTF-8 -Dsonar.sources=/var/lib/jenkins/workspace/pipeline/project/src -Dsonar.jacoco.reportPaths=/var/lib/jenkins/workspace/pipeline/project/target/jacoco.exec -Dsonar.junit.reportPaths=/var/lib/jenkins/workspace/pipeline/project/target/surefire-reports/ -Dsonar.java.binaries=**/target/classes"
         echo "currentBuild.result1: '${currentBuild.result}'"
	 }
}

stage('Build Image & Push to Docker'){
node('jenkinsslave22'){
echo "docker -version"

}
}
}
catch(exc){
     if (currentBuild.result == 'UNSTABLE') {
            mail to: 'team@example.com',
             subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"
        }
		throw exc 
}
        finally {
     if (currentBuild.result == 'SUCCESS') {
            mail to: 'ksnnarsy@gmail.com',
             subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             body: "pipeline is successful ${env.BUILD_URL}"
        } 
}

 }
 

