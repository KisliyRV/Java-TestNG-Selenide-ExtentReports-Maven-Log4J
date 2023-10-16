package ua.com.usource.common.core.pipeline

library identifier: "shared-library@$BRANCH",
        retriever: modernSCM([
                $class       : 'GitSCMSource',
                credentialsId: '{REPLACE_WITH_CREDENTIALS}',
                remote       : 'git@github.com:Zhuravl/Java-TestNG-Selenide-ExtentReports-Maven-Log4J.git'])

pipeline {
    agent any

    stages {
        stage("Start regression") {
            steps {
                slackSend message: "The Regression has been started!", channel: 'qualityassurance', tokenCredentialId: '{REPLACE_WITH_CREDENTIALS}'
                testsWithRerun(['TARGET_URL'     : TARGET_URL,
                                'BROWSER_NAME'   : BROWSER_NAME,
                                'BROWSER_VERSION': BROWSER_VERSION,
                                'TESTS_SCOPE'    : TESTS_SCOPE,
                                'TIMEOUT'        : TIMEOUT,
                                'THREADS_COUNT'  : THREADS_COUNT])
            }
        }
    }
    post {
        always {
            slackSend message: "The Regression has been completed!\nSee results here: $RUN_DISPLAY_URL", channel: 'qualityassurance', tokenCredentialId: '{REPLACE_WITH_CREDENTIALS}'
        }
    }
}