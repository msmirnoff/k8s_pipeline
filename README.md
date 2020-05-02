# Project Overview

Implementation of a deployment pipeline of a containerized application.
Utilises Docker, Jenkins and kubernetes via AWS EKS.

# Requirements

* DockerHub and AWS accounts
* EKS cluster set up, e.g. using `cf_ekscluster.yml` with the _right user_ (see https://docs.aws.amazon.com/eks/latest/userguide/troubleshooting.html#unauthorized). It appears that unless you already know Kubernetes well enough to do everything yourself, AWS assumes that user creating the cluster will be the only user initially accessing it - which makes _using an admin account to create a cluster that other users can access_ a use case that doesn't work out of the box. Perhaps create it as a new user with the relevant IAM and Cloudformation privileges and remove those privileges after creation, then use that user to access EKS. Someone familiar with K8S and EKS probably knows a better way.
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