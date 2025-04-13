pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'docker.io' // or your private registry URL
        IMAGE_NAME = 'selenium-docker'
        IMAGE_TAG = 'latest'
        DOCKER_COMPOSE_PATH = './docker-compose.yaml' // Path to your Docker Compose file
        DOCKER_USERNAME = 'guptachal'
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "Checking out the code from the repository."
                checkout scm
            }
        }

        stage('Build and Package') {
            steps {
                echo "Packaging the project into a JAR file."
                sh "mvn clean package -DskipTests"
            }
        }

        stage('Login to Docker Registry') {
            steps {
                script {
                    echo "Logging into Docker registry."
                    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh """
                            echo "$DOCKER_PASSWORD" | docker login ${DOCKER_REGISTRY} -u "$DOCKER_USERNAME" --password-stdin
                        """
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building the Docker image."
                    sh """
                        docker build -t ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG} .
                    """
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "Pushing the Docker image to the registry."
                    sh """
                        docker push ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG}
                    """
                    echo "Tagging Docker image with build number."
                    sh """
                        docker tag ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG} ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${env.BUILD_NUMBER}
                        docker push ${DOCKER_REGISTRY}/${DOCKER_USERNAME}/${IMAGE_NAME}:${env.BUILD_NUMBER}
                    """
                }
            }
        }

        stage('Run Tests in Docker') {
            steps {
                script {
                    echo "Running the tests using runner.sh inside a Docker container."
                    // Ensure runner.sh is executable and run it
                    sh "chmod +x runner.sh && ./runner.sh"
                }
            }
        }
    }

    post {
        always {
            echo "Cleaning up workspace and Docker resources after pipeline execution."
            // Prune unused Docker resources to free up disk space
            sh "docker system prune -f || true"
            // Clean up the workspace
            cleanWs()
        }
        failure {
            echo "Pipeline failed. Please check the logs for details."
            // Optionally, add steps here to send notifications (email, Slack, etc.)
        }
        success {
            echo "Pipeline executed successfully."
            // Optionally, archive artifacts or publish test reports here
        }
    }
}
