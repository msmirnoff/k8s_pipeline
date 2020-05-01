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
            }
        }
        stage('Push image to DockerHub') {
            steps {
            }
        }
        stage('set current kubectl context') {
            steps {
            }
        }
        stage('Deploy container') {
            steps {
            }
        }
     }
}
