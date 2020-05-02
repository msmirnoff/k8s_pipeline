pipeline {
    agent any
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
                withCredentials(
                    [usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'),
                    string(credentialsId: 'IMAGE_NAME', variable: 'IMAGE_NAME')]
                )
                {
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