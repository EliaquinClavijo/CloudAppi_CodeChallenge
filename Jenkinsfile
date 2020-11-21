pipeline {
  agent any
  stages {
    stage('Build JAR') {
      steps {
        dir('CLOUDAPPI.ApiUsers') {
          sh 'mvn clean package -DskipTests'
          sh 'docker build -t users-service:v1 .'
        }
        dir('docker-compose') {
          script {
            try {
              sh 'docker-compose stop users-service'
            } catch (Exception e) {
              echo 'Docker compose dont have a process running'
            }
          }
        }
      }
    }

    stage('docker-compose Deploy') {
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
  }
}
