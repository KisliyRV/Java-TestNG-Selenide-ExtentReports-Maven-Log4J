def call(Map<String, String> params) {

    int threadsCount = 1
    String testsScope = "ExampleTest#testExample"

    stage("Smoke test") {
        build job: 'ATExecutor',
                parameters: [string(name: 'TARGET_URL', value: params["TARGET_URL"]),
                             string(name: 'BROWSER_NAME', value: params["BROWSER_NAME"]),
                             string(name: 'BROWSER_VERSION', value: params["BROWSER_VERSION"]),
                             string(name: 'TESTS_SCOPE', value: testsScope),
                             string(name: 'TIMEOUT', value: params["TIMEOUT"]),
                             string(name: 'THREADS_COUNT', value: (threadsCount as String)),
                             string(name: 'BRANCH', value: params["BRANCH"])]
    }
}
