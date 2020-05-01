# Project Overview

Implementation of a deployment pipeline of a containerized application.
Utilises Docker, Jenkins and kubernetes via AWS EKS.

# Requirements

* DockerHub and AWS accounts
* EKS cluster set up, e.g. using `cf_ekscluster.yml`
* Jenkins with the repository integration, AWS pipeline, eksctl, kubectl, and docker cli tools set up

# Usage

TODO

# Repository file information

`Dockerfile` - Docker image build directives

`README.md` - readme

`cf_ekscluster.yml` - Cloudformation script to create the EKS cluster in a newly created VPC