pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                
                withMaven(maven:'maven3.6'){
                 sh 'mvn clean install'
            }
        }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
               withMaven(maven:'maven3.6'){
                 sh 'mvn deploy'
            }
            }
        }
    }
}