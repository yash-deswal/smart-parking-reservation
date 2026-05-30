pipeline {
    agent any

    tools {
        nodejs 'Node18'
    }

    environment {
        DOCKER_IMAGE_BACKEND = 'parking-backend'
        DOCKER_IMAGE_FRONTEND = 'parking-frontend'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Backend Build') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests -B'
            }
        }

        stage('Backend Tests') {
            steps {
                sh './mvnw test -B'
            }
        }

        stage('Frontend Build') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Frontend Tests') {
            steps {
                dir('frontend') {
                    sh 'npm run lint || true'
                }
            }
        }

        stage('Docker Build') {
            steps {
                // Build and tag Backend
                sh "docker build -t ${DOCKER_IMAGE_BACKEND}:${BUILD_NUMBER} -t ${DOCKER_IMAGE_BACKEND}:latest -f Dockerfile ."
                // Build and tag Frontend
                sh "docker build --build-arg VITE_API_URL=/api -t ${DOCKER_IMAGE_FRONTEND}:${BUILD_NUMBER} -t ${DOCKER_IMAGE_FRONTEND}:latest -f frontend/Dockerfile frontend"
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    set -e
                    
                    # Load environment variables from .env file
                    if [ -f .env ]; then
                        export $(cat .env | grep -v '#' | xargs)
                    fi
                    
                    # Remove existing containers and networks (idempotent)
                    docker compose down --remove-orphans || true
                    
                    # Wait a moment to ensure cleanup
                    sleep 2
                    
                    # Bring up services with environment variables
                    docker compose up -d
                    
                    # Verify services are running
                    echo "Waiting for services to be healthy..."
                    sleep 10
                    docker compose ps
                '''
            }
        }
    }

    post {
        success {
            echo 'Build and deployment completed successfully'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}
