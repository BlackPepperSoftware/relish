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
                    sh '[ -f /tmp/myserver.pid ] && (kill $(cat /tmp/myserver.pid) || echo "Old server gone")'
                    sh 'rm -f /tmp/myserver.pid'
                    sh 'rm -f /tmp/myserver.lock'
                    sh '/usr/local/sbin/daemonize ' +
                            '-E BUILD_ID=dontKillMe ' +
                            '-p /tmp/myserver.pid ' +
                            '-l /tmp/myserver.lock ' +
                            '-o /tmp/myserver.out ' +
                            '-c examples/selenide/website ' +
                            '/usr/bin/python -m SimpleHTTPServer 2>&1 > /tmp/myserver.runout'
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
                junit '**/TEST-*.xml'
                archiveArtifacts allowEmptyArchive: true, artifacts: 'relish-core/build/libs/relish-core-*.jar'
                archiveArtifacts allowEmptyArchive: true, artifacts: 'relish-selenide/build/libs/relish-selenide-*.jar'
                archiveArtifacts allowEmptyArchive: true, artifacts: 'relish-espresso/build/outputs/aar/relish-espresso-release.aar'
                sh '[ -f /tmp/myserver.pid ] && kill $(cat /tmp/myserver.pid)'
                sh 'rm -f /tmp/myserver.pid'
                sh 'rm -f /tmp/myserver.lock'
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
