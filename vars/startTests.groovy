def call(Map<String, String> params) {

    def run = params["RUN"]

    stage("Run tests #${run}") {
        build job: 'ATExecutor',
                parameters: [string(name: 'TARGET_URL', value: params["TARGET_URL"]),
                             string(name: 'BROWSER_NAME', value: params["BROWSER_NAME"]),
                             string(name: 'BROWSER_VERSION', value: params["BROWSER_VERSION"]),
                             string(name: 'TESTS_SCOPE', value: params["TESTS_SCOPE"]),
                             string(name: 'TIMEOUT', value: params["TIMEOUT"]),
                             string(name: 'THREADS_COUNT', value: params["THREADS_COUNT"]),
                             string(name: 'BRANCH', value: params["BRANCH"])]
    }
}
