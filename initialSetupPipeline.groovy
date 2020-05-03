pipeline {
    agent any
    stages {
        stage('create the cluster') {
            environment {
                STACK_NAME  = "kubeCluster"
                CF_TEMPLATE = "cf_ekscluster.yml"
            }
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-jenkins', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                    sh '''
                        aws cloudformation create-stack --stack-name $STACK_NAME --template-body file://"$CF_TEMPLATE" --capabilities CAPABILITY_NAMED_IAM 
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
    }
}