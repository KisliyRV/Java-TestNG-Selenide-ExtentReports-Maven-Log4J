node {

    env.PATH = "${tool 'Maven'}/bin:${env.PATH}"

    int MAX_RUNS = 5
    String scope = TESTS_SCOPE as String
    Integer threads = THREADS_COUNT as Integer

    echo "The Regression has been started!"
    stage('Checkout') {
        checkout([$class: 'GitSCM',
                  branches: [[name: '${BRANCH}']],
                  userRemoteConfigs: [[url: 'https://github.com/Zhuravl/Java-TestNG-Selenide-ExtentReports-Maven-Log4J.git']]])
    }
    def jobHelper = load 'vars/jobHelper.groovy' //Needs to be called after the checkout stage!
    stage('Check/Create ATExecutor Job') {
        jobHelper.createATExecutorJob()
    }
    stage('Smoke') {
        jobHelper.launchATExecutorJob(BRANCH as String, TARGET_URL as String, BROWSER_NAME as String, BROWSER_VERSION as String, 'SmokeTest', TIMEOUT as String, '1')
    }
    stage('Regression') {
        def threadsHelper = load 'vars/threadsHelper.groovy'
        def scopeHelper = load 'vars/scopeHelper.groovy'
        for (int run = 1; run <= MAX_RUNS; run++) {
            stage("Execution #${run}") {
                try {
                    jobHelper.launchJob(BRANCH as String, TARGET_URL as String, BROWSER_NAME as String, BROWSER_VERSION as String, scope, TIMEOUT as String, threads as String)
                } catch (Exception ignored) {
                    //Do nothing here and just move forward
                }
            }
            scope = scopeHelper.getFailedTests()
            if (scope == null || scope.isEmpty()) {
                //All tests passed - moving to the next stage
                return
            } else {
                //Some tests failed - reducing the number of threads and re-running with the updated scope (the failed tests only)
                threads = threadsHelper.reduceThreads(threads)
            }
        }
    }
    stage('Report') {

    }
    echo "The Regression has been completed!"
}
