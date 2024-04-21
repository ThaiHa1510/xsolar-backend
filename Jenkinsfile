pipeline {
    agent { label 'swarm' }
    options {
        timestamps()
        parallelsAlwaysFailFast()
    }
    triggers {
        githubPush()
    }

    stages {
        stage('checkout') {
            steps {
                git credentialsId: 'your-crendentail_id', url: 'gitlab_url'
            }
        }

        stage('test') {
            parallel {
                stage('java') {
                    steps {
                         sh '''
                        _ci/ruby
                        '''
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build Docker image within a block with Docker registry credentials
                withDockerRegistry([credentialsId: 'your-docker-hub-credentials-id', url: 'https://index.docker.io/v1/']) {
                    script {
                        docker.build('your-docker-image-name')
                        // Replace 'your-docker-image-name' with your desired image name
                        sh label: '' script: 'docker build -t your-docker-image-name .'
                        // Push Docker image to Docker Hub
                        sh lable: '' script: 'docker push your-docker-hub-username/your-docker-image-name:latest'
                        }
                }
            }
        }
        stage('cleanup') {
            steps {
                dir('web') {
                    sh '''
                        _ci/cleanup
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}

// vim: syntax=groovy :
