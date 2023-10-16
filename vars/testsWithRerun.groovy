def call(Map<String, String> params) {

    int MAX_RUNS = 5
    int run = 1
    boolean isFinished = false
    int threads = params["THREADS_COUNT"] as int

    String failedTests

    while (run <= MAX_RUNS && !isFinished) {

        if (run == 1) {
            smokeTest(params)

            params.put('RUN', (run as String))

            catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                startTests(params)
            }
        } else {
            threads = reduceThreads(threads)

            params["RUN"] = (run as String)
            params["THREADS_COUNT"] = (threads as String)
            params["TESTS_SCOPE"] = failedTests

            catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                startTests(params)
            }
        }

        stage("Analize run #${run}") {
            failedTests = getFailedTests()

            if (failedTests == null || failedTests == "") {
                echo "FINISHED"
                isFinished = true
            }
        }

        run += 1
    }
}