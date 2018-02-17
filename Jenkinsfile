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
                        sh './gradlew clean build :relish-core:sonarqube --info'
                    }
                    else{
                        bat 'gradlew.bat clean build --info'
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                archiveArtifacts allowEmptyArchive: true, artifacts: 'relish-core/build/libs/relish-core-*.aar'
                archiveArtifacts allowEmptyArchive: true, artifacts: 'relish-selenide/build/libs/relish-core-*.aar'
                archiveArtifacts allowEmptyArchive: true, artifacts: 'relish-espresso/build/outputs/aar/relish-espresso-release.aar'
            }
        }
        failure {
            slack "Build failed: ${currentBuild.result}", '#ff0000'
        }
    }
}

def slack(String msg, String color = '#000000') {
    slackSend channel: 'builds', color: color, message: msg, teamDomain: 'aspenshore', tokenCredentialId: 'timekeeper-slack'
}
