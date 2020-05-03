pipeline {
    agent any
    stages {
        stage('create the cluster') {
            environment {
                STACK_NAME  = "kubeCluster"
                CF_TEMPLATE = "eks_creation.yaml"
            }
            steps {
                withCredentials([
                    [$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-jenkins', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'],
                    [$class: 'StringBinding', credentialsId: 'REGION_NAME', variable: 'REGION_NAME']])
                {
                    sh '''
                        aws cloudformation --region $REGION_NAME create-stack --stack-name $STACK_NAME --template-body file://"$CF_TEMPLATE" --capabilities CAPABILITY_NAMED_IAM
                    '''
                }
            }
        }
    }
}