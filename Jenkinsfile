pipeline {
    agent any  // Run on any available agent

    tools {
        maven 'Maven 3'  // Name of your Maven installation in Jenkins
    }

    environment {
        DOCKER_IMAGE = 'blastza/authorization-server'  // Replace with your Docker image name
        DOCKER_TAG = 'latest'  // Use 'latest', a version tag, or a Jenkins variable like ${BUILD_NUMBER} for versioning
        DOCKER_REGISTRY = 'https://hub.docker.com/repositories/blastza'  // Docker Hub registry URL
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials'  // Jenkins credentials ID for Docker registry login
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

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry("${DOCKER_REGISTRY}", "${DOCKER_CREDENTIALS_ID}") {
                        sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    }
                }
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