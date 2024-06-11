package ua.com.usource.common.core.pipeline

pipeline {
    agent any

    stages {
        stage("Start regression") {
            steps {
                echo "The Regression has been started!"
                testsWithRerun(['TARGET_URL'     : TARGET_URL,
                                'BROWSER_NAME'   : BROWSER_NAME,
                                'BROWSER_VERSION': BROWSER_VERSION,
                                'TESTS_SCOPE'    : TESTS_SCOPE,
                                'TIMEOUT'        : TIMEOUT,
                                'THREADS_COUNT'  : THREADS_COUNT,
                                'BRANCH'         : BRANCH])
            }
        }
    }
    post {
        always {
            echo "The Regression has been completed!\nSee results here: $RUN_DISPLAY_URL"
        }
    }
}