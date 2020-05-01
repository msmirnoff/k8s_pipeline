# Project Overview

Implementation of a deployment pipeline of a containerized application.
Utilises Docker, Jenkins and kubernetes via AWS EKS.

# Requirements

* DockerHub and AWS accounts
* EKS cluster set up, e.g. using `cf_ekscluster.yml` with the _right user_ (see https://docs.aws.amazon.com/eks/latest/userguide/troubleshooting.html#unauthorized)
* Jenkins server with:
    * repository integration
    * AWS pipeline
    * docker installed
    * Jenkins able to run docker commands (e.g. via `usermod -a -G docker jenkins` but note that this will mean that Jenkins might as well run as root. There probably is a more secure way.)
    * Installed eksctl
    * installed kubectl

# Usage

TODO

# Repository file information

`Dockerfile` - Docker image build directives

`README.md` - readme

`cf_ekscluster.yml` - Cloudformation script to create the EKS cluster in a newly created VPC

`Jenkinsfile` - Jenkins build instructions