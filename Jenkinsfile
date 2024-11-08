pipeline {
    agent any  // Run on any available agent

    tools {
        maven 'Maven 3'  // Name of your Maven installation in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout from the Git repository
                git 'https://github.com/L-TeA/authorization-server.git'
            }
        }

        stage('Clean and Build') {
            steps {
                // Run the Maven clean and install commands
                sh 'mvn clean install -DskipTests'
            }
        }

/*         stage('Test') {
            steps {
                // Run the Maven test command
                sh 'mvn test'
            }
        } */
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