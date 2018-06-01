#!/usr/bin/env groovy Jenkinsfile

def server = Artifactory.server "Artifactory"
def gradle = Artifactory.newGradleBuild()
def buildInfo = Artifactory.newBuildInfo()
buildInfo.env.capture = true

pipeline() {

    agent any

    triggers {
        pollSCM('H/10 * * * *')
    }

    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    environment {
        SLACK_AUTOMATION_CHANNEL = "#automation"
        SLACK_AUTOMATION_TOKEN = credentials("jenkins-ci-integration-token")
        JENKINS_HOOKS = credentials("morning-at-lohika-jenkins-ci-hooks")
    }

    stages {

        stage('Configuration') {
            steps {
                script {
                    gradle.useWrapper = true
                    gradle.deployer server: server, repo: 'morning-at-lohika-snapshots'
                }
            }
        }

        stage('Gradle build and deploy') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'master') {
                        gradle.run rootDir: "./", buildFile: 'build.gradle.kts', tasks: 'clean build artifactoryPublish'
                    } else {
                        gradle.run rootDir: "./", buildFile: 'build.gradle.kts', tasks: 'clean build'
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                publishHTML(target: [
                    allowMissing         : true,
                    alwaysLinkToLastBuild: false,
                    keepAll              : true,
                    reportDir            : 'build/reports/tests/test',
                    reportFiles          : 'index.html',
                    reportName           : "Test Summary"
                ])
                junit testResults: 'build/test-results/test/*.xml', allowEmptyResults: true
                server.publishBuildInfo buildInfo
            }
        }

        success {
            script {
                dir("${env.WORKSPACE}") {
                    archiveArtifacts 'build/libs/*.jar'
                }

                slackSend(
                    baseUrl: "${env.JENKINS_HOOKS}",
                    token: "${env.SLACK_AUTOMATION_TOKEN}",
                    channel: "${env.SLACK_AUTOMATION_CHANNEL}",
                    botUser: true,
                    color: "good",
                    message: "BUILD SUCCESS: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\nCheck console output at: ${env.BUILD_URL}"
                )
            }
        }

        failure {
            script {
                def message = "BUILD SUCCESS: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\nCheck console output at: ${env.BUILD_URL}"
                slackSend baseUrl: JENKINS_HOOKS, color: "danger", message: message.toString()

                slackSend(
                    baseUrl: "${env.JENKINS_HOOKS}",
                    token: "${env.SLACK_AUTOMATION_TOKEN}",
                    channel: "${env.SLACK_AUTOMATION_CHANNEL}",
                    botUser: true,
                    color: "danger",
                    message: "BUILD FAILURE: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\nCheck console output at: ${env.BUILD_URL}"
                )
            }
        }
    }
}
