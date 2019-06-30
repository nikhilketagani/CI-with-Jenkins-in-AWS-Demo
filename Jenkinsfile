node('jenkinsslave22') {
    try {
      def mvnHome
      currentBuild.result?:'SUCCESS'
     stage('Preparation') { // for display purposes
       //Get some code from a GitHub repository
      git 'https://github.com/nikhilketagani/CI-with-Jenkins-in-AWS-Demo.git'
      // Get the Maven tool.
      // ** NOTE: This 'Maven361' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'Maven361'
   }   
   stage('Unit Test') {
       // Run the maven test
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
       sh "${scannerHome}/bin/sonar-scanner -Dsonar.host.url=http://35.200.227.87:9000/ -Dsonar.login=6c64b9c6e7a0c8449cdea0acbe023a47a4d1bd2d -Dsonar.projectVersion=1.0 -Dsonar.projectKey=PROJECT -Dsonar.sourceEncoding=UTF-8 -Dsonar.sources=/var/lib/jenkins/workspace/pipeline/project/src -Dsonar.jacoco.reportPaths=/var/lib/jenkins/workspace/pipeline/project/target/jacoco.exec -Dsonar.junit.reportPaths=/var/lib/jenkins/workspace/pipeline/project/target/surefire-reports/ -Dsonar.java.binaries=**/target/classes"
         echo "currentBuild.result1: '${currentBuild.result}'"
	 }
}

stage('Build Image & Push to Docker'){

//echo "docker -version"
//sh "docker pull jenkins/jenkins"
 withEnv(["MVN_HOME=$mvnHome"]) {
          if (isUnix()) {
             sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean install' ;
             
          } else {
             bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean install/)
            
          }
       }
sh 'cp /var/lib/jenkins/workspace/pipeline/project/target/project-1.0-RAMA.war .'	   
	   
 sh 'docker build -t nikhilketagani/tomcatwar:${BUILD_NUMBER} .' 
 sh 'docker image ls'
 withCredentials([usernamePassword(credentialsId: 'b6a33171-7108-4d7c-b94f-5c533b4d3af6', passwordVariable: 'password', usernameVariable: 'username')]) {
   
	sh 'docker login -u=${username}  -p=${password}'
	sh 'docker push nikhilketagani/tomcatwar:${BUILD_NUMBER}'
}

}
	    	    stage('deploy on Kubernetes cluster'){
sh 'kubectl version'
			    //for deployment of new pods with conatiners
		    //sh 'kubectl delete -f tomcatwarservice.yaml'
		    //sh 'kubectl delete -f tomcatwardeployment.yaml'
		    //sh 'kubectl create -f tomcatwardeployment.yaml'
		    //sh 'kubectl create -f tomcatwarservice.yaml'
			    //for rollout update of current containers in pods with newly build container
		   sh 'kubectl set image deployments/tomcatwar-deployment tomcatwar-conatiner=nikhilketagani/tomcatwar:${BUILD_NUMBER}'
		    sh 'kubectl rollout status deployment tomcatwar-deployment'
}
	   
	    stage('Selenium Testing'){
		      withEnv(["MVN_HOME=$mvnHome"]) {
sh '"$MVN_HOME/bin/mvn"  -Dmaven.test.failure.ignore  clean verify'
		     junit 'target/failsafe-reports/**/*.xml'
		      }    
}
    }
catch(exc){
	//catching the failure and set the currentBuild result to failure
    currentBuild.result = 'FAILURE'
		throw exc 
}
        finally {
		//send email notification for both build failure and build success
     if (currentBuild.result == 'SUCCESS'||currentBuild.result == null) {
            mail to: 'ksnnarsy@gmail.com',
             subject: "$JOB_NAME - Build # $BUILD_NUMBER - SUCCESS!",
             body: "pipeline is successfully run. $JOB_NAME - Build # $BUILD_NUMBER - SUCCESS .Check console output at $BUILD_URL to view the results."
        } 
         if (currentBuild.result == 'FAILURE') {
            mail to: 'ksnnarsy@gmail.com',
             subject: "$JOB_NAME - Build # $BUILD_NUMBER - FAILURE!",
             body: "Something is wrong with $JOB_NAME - Build # $BUILD_NUMBER - FAILURE! .Check console output at $BUILD_URL to view the results."
        }
          
}

 }
 
 
 
