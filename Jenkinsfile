pipeline {
    agent any
    environment {
        IMAGE_NAME   = credentials('IMAGE_NAME')
    }
    stages {
        stage('Linting') {
            steps {
                sh '''
                    hadolint Dockerfile
                '''
            }
        }
        stage('Build Docker image') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD']]) {
                    sh '''
                        docker build -t "$DOCKER_USERNAME"/"$IMAGE_NAME":"$BUILD_ID" .
                    '''
                }
            }
        }
        stage('Push image to DockerHub') {
            steps {
                sh '''
                    echo TODO
                '''
            }
        }
        stage('set current kubectl context') {
            steps {
                sh '''
                    echo TODO
                '''
            }
        }
        stage('Deploy container') {
            steps {
                sh '''
                    echo TODO
                '''
            }
        }
    }
}