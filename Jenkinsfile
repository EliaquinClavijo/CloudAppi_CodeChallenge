pipeline {
  agent any
  stages {
    stage('build jar') {
      steps {
        dir('docker-compose') {
          script {
            try {
              sh 'docker-compose up -d cloudappidb'
            } catch (Exception e) {
              echo 'allready its running'
            }
          }
        }
        dir('CLOUDAPPI.ApiUsers') {
          sh 'mvn clean package'
          sh 'docker build -t users-service:v1 .'
        }
      }
    }

    stage('docker-compose deploy') {
      steps {
        dir('docker-compose') {
          script {
            try {
              sh 'docker-compose up -d'
            } catch (Exception e) {
              echo 'Docker compose throw an error'
            }
          }
        }
      }
    }

    stage('sonarqube deploy'){
      steps {
        dir('CLOUDAPPI.ApiUsers') {          
              sh 'mvn test sonar:sonar -Dsonar.projectKey=cloudAppi -Dsonar.host.url=http://localhost:9000  -Dsonar.login=7ee0906dd7a0ab120cb66e4f8e0a3177f4b09d9e'
        }
      }
    }
  }
}
