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
                    [$class: 'StringBinding',credentialsId: 'IMAGE_NAME', variable: 'IMAGE_NAME']])
                {
                    sh '''
                        docker build -t "$DOCKER_USERNAME"/"$IMAGE_NAME":"$BUILD_ID" .
                    '''
                }
            }
        }
        stage('Push image to DockerHub') {
            steps {
                withCredentials(
                    [usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'),
                    string(credentialsId: 'IMAGE_NAME', variable: 'IMAGE_NAME')]
                )
                {
                    sh '''
                        docker login --username "$DOCKER_USERNAME" --password "$DOCKER_PASSWORD"
                        docker push "$DOCKER_USERNAME"/"$IMAGE_NAME":"$BUILD_ID"
                    '''
                }
            }
        }
        stage('set current kubectl context') {
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-jenkins',
                    accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                    secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'],
                    [$class: 'StringBinding',
                    credentialsId: 'REGION_NAME',
                    variable: 'REGION_NAME'],
                    [$class: 'StringBinding',
                    credentialsId: 'CLUSTER_NAME',
                    variable: 'CLUSTER_NAME']])
                {
                    sh '''
                        aws eks --region "$REGION_NAME" update-kubeconfig --name "$CLUSTER_NAME"
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