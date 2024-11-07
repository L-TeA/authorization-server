pipeline {
    agent any  // This means it will run on any available agent

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your repository
                checkout 'https://github.com/L-TeA/authorization-server.git'
            }
        }

        stage('Clean and Build') {
            steps {
                script {
                    // Clean and build the Maven project
                    sh 'mvn clean install'  // Run Maven command to clean and build the project
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests if you have them configured in your Maven project
                    sh 'mvn test'
                }
            }
        }
    }

    post {
        success {
            echo 'Build and tests successful!'
        }
        failure {
            echo 'Build or tests failed.'
        }
    }
}