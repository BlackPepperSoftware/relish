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
                    lock(resource: 'relish-dev') {
                        sh 'sleep 10'
                        sh 'if [ -f /tmp/myserver.pid ]; then (kill $(cat /tmp/myserver.pid) || echo "Old server gone"); fi'
                        sh 'rm -f /tmp/myserver.pid'
                        sh 'rm -f /tmp/myserver.lock'
                        sh '/usr/local/sbin/daemonize ' +
                                '-E BUILD_ID=dontKillMe ' +
                                '-p /tmp/myserver.pid ' +
                                '-l /tmp/myserver.lock ' +
                                '-o /tmp/myserver.out ' +
                                '-c "$PWD/examples/selenide/website" ' +
                                '/usr/bin/python -m SimpleHTTPServer 2>&1 > /tmp/myserver.runout'
                        if(isUnix()){
                            sh './gradlew clean build check unitCoverageReport sonarqube --info --stacktrace'
                        }
                        else{
                            bat 'gradlew.bat clean build check unitCoverageReport sonarqube --info --stacktrace'
                        }
                    }
                }
            }
        }
        stage('Publish') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'master') {
                        sh "git tag -a '0.0.${BUILD_NUMBER}' -m 'Release 0.0.${BUILD_NUMBER}'"
                        sh "git push --tags"
                        sh "github-release release --user dogriffiths --repo relish --tag '0.0.${BUILD_NUMBER}' --name 'Relish' --description 'Version 0.0.${BUILD_NUMBER}' --pre-release"
                        sh "cp relish-core/build/libs/relish-core-1.0-SNAPSHOT.jar 'relish-core-0.0.${BUILD_NUMBER}.jar'"
                        sh "cp relish-selenide/build/libs/relish-selenide-1.0-SNAPSHOT.jar 'relish-selenide-0.0.${BUILD_NUMBER}.jar'"
                        sh "cp relish-espresso/build/outputs/aar/relish-espresso-release.aar 'relish-espresso-0.0.${BUILD_NUMBER}.aar'"
                        sh "github-release upload --user dogriffiths --repo relish --tag '0.0.${BUILD_NUMBER}' --name 'relish-core-0.0.${BUILD_NUMBER}.jar' --file 'relish-core-0.0.${BUILD_NUMBER}.jar'"
                        sh "github-release upload --user dogriffiths --repo relish --tag '0.0.${BUILD_NUMBER}' --name 'relish-selenide-0.0.${BUILD_NUMBER}.jar' --file 'relish-selenide-0.0.${BUILD_NUMBER}.jar'"
                        sh "github-release upload --user dogriffiths --repo relish --tag '0.0.${BUILD_NUMBER}' --name 'relish-espresso-0.0.${BUILD_NUMBER}.jar' --file 'relish-espresso-0.0.${BUILD_NUMBER}.aar'"
                        sh "rm -f 'relish-core-0.0.${BUILD_NUMBER}.jar'"
                        sh "rm -f 'relish-selenide-0.0.${BUILD_NUMBER}.jar'"
                        sh "rm -f 'relish-espresso-0.0.${BUILD_NUMBER}.aar'"
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
                sh 'if [ -f /tmp/myserver.pid ]; then (kill $(cat /tmp/myserver.pid) || echo "Old server gone"); fi'
                sh 'rm -f /tmp/myserver.pid'
                sh 'rm -f /tmp/myserver.lock'
            }
        }
    }
}

