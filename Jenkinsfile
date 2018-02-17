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
                        sh './gradlew clean build check sonarqube --info --stacktrace'
                    }
                    else{
                        bat 'gradlew.bat clean build check sonarqube --info --stacktrace'
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                archiveArtifacts allowEmptyArchive: true, artifacts: 'relish-core/build/libs/relish-core-*.jar'
                archiveArtifacts allowEmptyArchive: true, artifacts: 'relish-selenide/build/libs/relish-selenide-*.jar'
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
