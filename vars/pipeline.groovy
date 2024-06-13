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
    stage('Smoke') {
        sh "mvn clean test site -Durl=${TARGET_URL} -Dbrowser=${BROWSER_NAME} -Dversion=${BROWSER_VERSION} -Dtest=ExampleTest -Dtimeout=${TIMEOUT} -DthreadCount=1"
    }
    stage('Regression') {
        def threadsHelper = load 'threadsHelper.groovy'
        for (int run = 1; run <= MAX_RUNS; run++) {
            stage("Execution #${run}") {
                try {
                    sh "mvn clean test site -Durl=${TARGET_URL} -Dbrowser=${BROWSER_NAME} -Dversion=${BROWSER_VERSION} -Dtest=${scope} -Dtimeout=${TIMEOUT} -DthreadCount=${threads}"
                } catch (Exception ignored) {
                    //Do nothing here and just move forward
                }
            }
            scope = getFailedTests()
            if (scope == null || scope.isEmpty()) {
                //All tests passed - moving to the next stage
                return
            } else {
                //Some tests failed - reducing the number of threads and re-running with the updated scope (the failed tests only)
                threads = threadsHelper.reduceThreads(threads)
            }
        }
    }
    echo "The Regression has been completed!"
}

static String getFailedTests() {
    return "ExampleThreeTest"
}
