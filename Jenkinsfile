pipeline {
    agent any

    environment {
        // Docker Registry Info (replace with your own values)
        DOCKER_REGISTRY = 'docker.io' // or your private registry URL
        DOCKER_USERNAME = 'guptachal'
        DOCKER_PASSWORD = 'Ach@l1234567890'
        IMAGE_NAME = 'selenium-docker'
        IMAGE_TAG = 'latest' // or use versioning, e.g., '1.0'
        DOCKER_COMPOSE_PATH = './docker-compose.yaml' // Path to your Docker Compose file
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Checkout the code from the repository
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image using the Dockerfile
                    sh """
                        docker build -t ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG} .
                    """
                }
            }
        }

        stage('Login to Docker Registry') {
            steps {
                script {
                    // Login to Docker Registry using credentials
                    docker.withRegistry("https://${DOCKER_REGISTRY}", "${DOCKER_USERNAME}") {
                        // Use the credentials from Jenkins
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Push the Docker image to the registry
                    sh """
                        docker push ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG}
                    """
                }
            }
        }

        stage('Run Docker Compose') {
            steps {
                script {
                    // Use Docker Compose to spin up the services
                    sh """
                        docker-compose -f ${DOCKER_COMPOSE_PATH} up --build
                    """
                }
            }
        }
    }

    post {
        always {
            // Clean up resources after the pipeline runs
            cleanWs()
        }
    }
}
