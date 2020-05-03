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
                withCredentials([
                    [$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'],
                    [$class: 'StringBinding', credentialsId: 'IMAGE_NAME', variable: 'IMAGE_NAME']])
                {
                    sh '''
                        docker build -t "$DOCKER_USERNAME"/"$IMAGE_NAME":"$BUILD_ID" .
                    '''
                }
            }
        }
        stage('Scan Docker image') {
            steps{
                withCredentials([
                    [$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'],
                    [$class: 'StringBinding', credentialsId: 'IMAGE_NAME', variable: 'IMAGE_NAME']])
                {
                    aquaMicroscanner imageName: "${DOCKER_USERNAME}/${IMAGE_NAME}:${BUILD_ID}", notCompliesCmd: 'exit 4', onDisallowed: 'fail', outputFormat: 'html'
                }
            }
        }
        stage('Push image to DockerHub') {
            steps {
                withCredentials([
                    [$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'],
                    [$class: 'StringBinding', credentialsId: 'IMAGE_NAME', variable: 'IMAGE_NAME']])
                {
                    sh '''
                        docker login --username "$DOCKER_USERNAME" --password "$DOCKER_PASSWORD"
                        docker push "$DOCKER_USERNAME"/"$IMAGE_NAME":"$BUILD_ID"
                    '''
                }
            }
        }
        stage('Remove Unused docker image') {
            steps {
                withCredentials([
                    [$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'],
                    [$class: 'StringBinding', credentialsId: 'IMAGE_NAME', variable: 'IMAGE_NAME']])
                {
                    sh '''
                        docker rmi "$DOCKER_USERNAME"/"$IMAGE_NAME":"$BUILD_ID"
                    '''
                }
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