node {

    int MAX_RUNS = 5
    String scope = TESTS_SCOPE as String
    Integer threads = THREADS_COUNT as Integer

    echo "The Regression has been started!"
    stage('Checkout') {
        checkout([$class: 'GitSCM',
                  branches: [[name: '${BRANCH}']],
                  userRemoteConfigs: [[url: 'https://github.com/Zhuravl/Java-TestNG-Selenide-ExtentReports-Maven-Log4J.git']]])
    }
    def jobHelper = load 'pipeline/jobHelper.groovy' //Needs to be called after the checkout stage!
    stage('Check/Create ATExecutor Job') {
        jobHelper.createATExecutorJob()
    }
//    stage('Smoke') {
//        def result = jobHelper.launchATExecutorJob(BRANCH as String, TARGET_URL as String, BROWSER_NAME as String, BROWSER_VERSION as String, 'SmokeTest', TIMEOUT as String, '1')
//        if (result != hudson.model.Result.SUCCESS) {
//            error "The Smoke Test has been failed!"
//        }
//    }
    stage('Regression') {
        def threadsHelper = load 'pipeline/threadsHelper.groovy'
//        def scopeHelper = load 'pipeline/scopeHelper.groovy'
        for (int run = 1; run <= MAX_RUNS; run++) {
            stage("Execution #${run}") {
                jobHelper.launchATExecutorJob(BRANCH as String, TARGET_URL as String, BROWSER_NAME as String, BROWSER_VERSION as String, scope, TIMEOUT as String, threads as String)
            }
//            scope = scopeHelper.getFailedTests()
            copyArtifacts(projectName: 'ATExecutor', selector: lastCompleted(), filter: 'target/surefire-reports/testng-failed.xml', target: 'target/surefire-reports', flatten: true)
            def copiedFile = load 'target/surefire-reports/testng-failed.xml'
            echo "Copied file: '${copiedFile}'"
            if (copiedFile) {
                def xmlFile = new XmlSlurper().parse(copiedFile)
                scope = xmlFile.test.classes.class.'@name'.toString().replaceAll("ua\\.com\\.usource\\.tests\\.", ",").replaceFirst(",", "")
            } else {
                scope = null
            }
            if (scope == null || scope.isEmpty()) {
                echo "All regression tests have been passed!"
                return
            } else {
                echo "Some regression tests have been failed - reducing the number of threads and re-running with the updated scope"
                threads = threadsHelper.reduceThreads(threads)
            }
        }
    }
    stage('Report') {

    }
    echo "The Regression has been completed!"
}
