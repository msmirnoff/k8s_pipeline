pipeline {
    agent any
    environment {
        $BLUEGREEN = sh (returnStdout: true, script: "grep app loadbalancer.yaml | awk '{print $2}'").trim()
    }
    stages {
        stage('Preflight checks') {
            steps {
                sh '''
                    if [[ "$BLUEGREEN" != "blue" && "$BLUEGREEN" != "green" ]]; then
                        echo "Undefined deployment target"
                        exit 1
                    fi
                '''
            }
        }
        stage('Linting') {
            steps {
                sh '''
                    hadolint Dockerfile
                    sh "tidy -q -e html/*.html"
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
        stage('set the kubectl context') {
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-jenkins', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'],
                    [$class: 'StringBinding', credentialsId: 'REGION_NAME', variable: 'REGION_NAME'],
                    [$class: 'StringBinding', credentialsId: 'CLUSTER_NAME', variable: 'CLUSTER_NAME']])
                {
                    sh '''
                        aws eks --region "$REGION_NAME" update-kubeconfig --name "$CLUSTER_NAME"
                    '''
                }
            }
        }
        stage('Deploy container') {
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-jenkins', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'],
                    [$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'],
                    [$class: 'StringBinding', credentialsId: 'IMAGE_NAME', variable: 'IMAGE_NAME']])
                {
                    sh '''
                        envsubst < replication.yaml | kubectl apply -f -
                    '''
                }
            }
        }
        stage('Check that it is ok to make this the active environment') {
            steps {
                input "Switch to this environment?"
            }
        }
        stage('Set deployed environment as active') {
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-jenkins', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']])
                {
                    sh '''
                        kubectl apply -f loadbalancer.yaml
                    '''
                }
            }
        }
    }
}