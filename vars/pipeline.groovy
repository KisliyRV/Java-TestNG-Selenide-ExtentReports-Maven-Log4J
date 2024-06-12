node {

    env.PATH = "${tool 'Maven'}/bin:${env.PATH}"

    int MAX_RUNS = 5
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
        for (int run = 1; run <= MAX_RUNS; run++) {
            stage("Execution #${run}") {
                try {
                    sh "mvn clean test site -Durl=${TARGET_URL} -Dbrowser=${BROWSER_NAME} -Dversion=${BROWSER_VERSION} -Dtest=${scope} -Dtimeout=${TIMEOUT} -DthreadCount=${threads}"
                } finally {
                    scope = getFailedTests()
                    if (scope == null || scope.isEmpty()) {
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
    echo "The Regression has been completed!"
}

static String getFailedTests() {
    echo "Getting failed tests.."
    String result = null
    echo "The failed tests scope is: '${result}'"
    return result
}

static int reduceThreads(int threads) {
    echo "Reducing the number of threads from ${threads}.."
    int result = 1
    if (threads > 2) {
        result = (int) (threads * 0.7)
    }
    echo "Reduced number of threads to ${result}"
    return result
}
