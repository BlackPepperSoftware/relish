#!groovy

pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {
        stage('SCM Checkout'){
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                script {
                    if(isUnix()){
                        sh './gradlew clean build --info'
                    }
                    else{
                        bat 'gradlew.bat clean build --info'
                    }
                }
            }
        }
    }
}
