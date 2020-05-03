# Project Overview

Implementation of a deployment pipeline of a containerized application.
Utilises Docker, Jenkins and kubernetes via AWS EKS.

# Requirements

* AWS account and a user with sufficient privileges
* A Docker registry account. DockerHub is assumed.
* Jenkins server with:
    * repository integration
    * AWS pipeline plugin
    * BlueOcean plugin
    * aqua Microscanner plugin
    * docker installed
    * Jenkins able to run docker commands (e.g. via `usermod -a -G docker jenkins` but note that this will mean that Jenkins might as well run as root. There probably is a more secure way.)
    * Installed eksctl
    * installed kubectl
    * AWS credentials set up
    * Other credentials (container registry, aqua API Key, etc) set up
    * Environment variables set up as needed (docker image parameters, region, etc) this is mostly for convenience and can be hardcoded instead
    * Sufficient IAM privileges to create the environment (these privileges can be removed after initial creation)
    * Sufficient privileges to deploy to EKS

# Usage

## Initial set up

Set up a custom pipeline in Jenkins as a Pipeline script from SCM, and set it to be the `initialSetupPipeline.groovy` script.

After running, revoke unneeded AWS privileges from Jenkins (generally anything except EKS access).

## Build and Deploy

Primary Jenkins pipeline is defined in Jenkinsfile.



# Repository file information

`Dockerfile` - Docker image build directives

`README.md` - readme

`initialSetupPipeline.groovy` - one-shot Jenkins pipeline to create the EKS cluster and supporting resources

`eks_creation.yml` - Cloudformation script to create the EKS cluster in a newly created VPC

`Jenkinsfile` - Jenkins build instructions