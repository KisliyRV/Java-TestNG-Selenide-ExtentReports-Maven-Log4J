node {

    env.PATH = "${tool 'Maven'}/bin:${env.PATH}"

    Integer MAX_RUNS = 5
    String scope = "${TESTS_SCOPE}" as String
    Integer threads = "${THREADS_COUNT}" as Integer

    echo "The Regression has been started!"
    stage('Checkout') {
        checkout([$class: 'GitSCM',
                  branches: [[name: '${BRANCH}']],
                  userRemoteConfigs: [[url: 'https://github.com/Zhuravl/Java-TestNG-Selenide-ExtentReports-Maven-Log4J.git']]])
    }
//    stage('Smoke') {
//        sh "mvn clean test site -Durl=${TARGET_URL} -Dbrowser=${BROWSER_NAME} -Dversion=${BROWSER_VERSION} -Dtest=ExampleTest -Dtimeout=${TIMEOUT} -DthreadCount=1"
//    }
    stage('Regression') {
        for (Integer run in 1..MAX_RUNS) {
            stage("Execution #${run}") {
                try {
                    sh "mvn clean test site -Durl=${TARGET_URL} -Dbrowser=${BROWSER_NAME} -Dversion=${BROWSER_VERSION} -Dtest=${scope} -Dtimeout=${TIMEOUT} -DthreadCount=10"
                } finally {
                    scope = "" //getFailedTests()
                    if (scope == null || scope == "") {
                        //All tests passed - moving to the next stage
                        return
                    } else {
                        //Some tests failed - reducing the number of threads and re-running with the updated scope (the failed tests only)
                        threads = reduceThreads(threads)
                    }
                }
            }
        }
    }
    post {
        always {
            echo "The Regression has been completed!\nSee results here: ${RUN_DISPLAY_URL}"
        }
    }
}