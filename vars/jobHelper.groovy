static def getATExecutorJobName() {
    return 'ATExecutor'
}
def launchATExecutorJob(String branch, String targetUrl, String browserName, String browserVersion, String testsScope, String timeout, String threadsCount) {
    def job = Jenkins.instance.getItem(getATExecutorJobName())
    if (job) {
        def parameters = [
                new hudson.model.StringParameterValue('BRANCH', branch),
                new hudson.model.StringParameterValue('TARGET_URL', targetUrl),
                new hudson.model.StringParameterValue('BROWSER_NAME', browserName),
                new hudson.model.StringParameterValue('BROWSER_VERSION', browserVersion),
                new hudson.model.StringParameterValue('TESTS_SCOPE', testsScope),
                new hudson.model.StringParameterValue('TIMEOUT', timeout),
                new hudson.model.StringParameterValue('THREADS_COUNT', threadsCount)
        ]
        def paramsAction = new hudson.model.ParametersAction(parameters) as Object
        def causeAction = new hudson.model.CauseAction(new hudson.model.Cause.UserIdCause()) as Object
        job.scheduleBuild2(0, paramsAction, causeAction)
        echo "The '${getATExecutorJobName()}' job has been successfully triggered..."
    } else {
        error "Failed to find the '${getATExecutorJobName()}' job after attempting to create it!"
    }
}
def createATExecutorJob() {
    def jobExists = false
    try {
        jobExists = Jenkins.instance.getItem(getATExecutorJobName()) != null
    } catch (Exception e) {
        echo "Error checking job existence: ${e.message}"
    }

    if (!jobExists) {
        echo "The '${getATExecutorJobName()}' job does not exist. Creating the job..."
        def jobXml = """
            <project>
              <actions/>
              <description>Job for execution of the automated tests</description>
              <keepDependencies>false</keepDependencies>
              <properties>
                <jenkins.model.BuildDiscarderProperty>
                  <strategy class="hudson.tasks.LogRotator">
                    <daysToKeep>-1</daysToKeep>
                    <numToKeep>20</numToKeep>
                    <artifactDaysToKeep>-1</artifactDaysToKeep>
                    <artifactNumToKeep>-1</artifactNumToKeep>
                  </strategy>
                </jenkins.model.BuildDiscarderProperty>
                <com.coravy.hudson.plugins.github.GithubProjectProperty plugin="github@1.39.0">
                  <projectUrl>https://github.com/Zhuravl/Java-TestNG-Selenide-ExtentReports-Maven-Log4J/</projectUrl>
                  <displayName></displayName>
                </com.coravy.hudson.plugins.github.GithubProjectProperty>
                <hudson.model.ParametersDefinitionProperty>
                  <parameterDefinitions>
                    <hudson.model.StringParameterDefinition>
                        <name>TARGET_URL</name>
                        <description>The target site URL</description>
                        <defaultValue>https://usource.com.ua/</defaultValue>
                        <trim>true</trim>
                    </hudson.model.StringParameterDefinition>
                    <hudson.model.StringParameterDefinition>
                        <name>BROWSER_NAME</name>
                        <description>The target browser name ('chrome', 'firefox', 'ie', 'opera', or 'edge')</description>
                        <defaultValue>chrome</defaultValue>
                        <trim>true</trim>
                    </hudson.model.StringParameterDefinition>
                    <hudson.model.StringParameterDefinition>
                        <name>BROWSER_VERSION</name>
                        <description>The browser version, is optional (the latest by default)</description>
                        <trim>true</trim>
                    </hudson.model.StringParameterDefinition>
                    <hudson.model.StringParameterDefinition>
                        <name>TESTS_SCOPE</name>
                        <description>Name of tests to execute: - '*Test' - to run all existing tests - 'MyFirstTest' - to run the whole class - 'MyFirstTest#firstMethod' or 'MyFirstTest#firstMethod+secondMethod' or 'MyFirstTest#firstMethod,MySecondTest#firstMethod' - to run the specific test(s)</description>
                        <defaultValue>*Test</defaultValue>
                        <trim>true</trim>
                    </hudson.model.StringParameterDefinition>
                    <hudson.model.StringParameterDefinition>
                        <name>TIMEOUT</name>
                        <description>The MAX timeout value (in seconds), is optional (15 sec by default)</description>
                        <trim>false</trim>
                    </hudson.model.StringParameterDefinition>
                    <hudson.model.StringParameterDefinition>
                        <name>THREADS_COUNT</name>
                        <description>To specify how many threads should be allocated for this execution, optional (1 thread by default)</description>
                        <defaultValue>10</defaultValue>
                        <trim>true</trim>
                    </hudson.model.StringParameterDefinition>
                    <hudson.model.StringParameterDefinition>
                        <name>BRANCH</name>
                        <description>Git branch name to use</description>
                        <defaultValue>master</defaultValue>
                        <trim>false</trim>
                    </hudson.model.StringParameterDefinition>
                    </parameterDefinitions>
                </hudson.model.ParametersDefinitionProperty>
              </properties>
              <scm class="hudson.plugins.git.GitSCM" plugin="git@5.2.2">
                <configVersion>2</configVersion>
                <userRemoteConfigs>
                  <hudson.plugins.git.UserRemoteConfig>
                    <url>git@github.com:Zhuravl/Java-TestNG-Selenide-ExtentReports-Maven-Log4J.git</url>
                    <credentialsId>b89238f3-a11f-4914-8e7d-e9947584c14f</credentialsId>
                  </hudson.plugins.git.UserRemoteConfig>
                </userRemoteConfigs>
                <branches>
                  <hudson.plugins.git.BranchSpec>
                    <name>*/\${BRANCH}</name>
                  </hudson.plugins.git.BranchSpec>
                </branches>
                <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
                <gitTool>Default</gitTool>
                <submoduleCfg class="empty-list"/>
                <extensions/>
              </scm>
              <canRoam>true</canRoam>
              <disabled>false</disabled>
              <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
              <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
              <triggers/>
              <concurrentBuild>false</concurrentBuild>
              <builders>
                  <hudson.tasks.Maven>
                    <targets>clean test site</targets>
                    <mavenName>Maven 3.9.7</mavenName>
                    <properties>url=\${TARGET_URL} browser=\${BROWSER_NAME} version=\${BROWSER_VERSION} test=\${TESTS_SCOPE} timeout=\${TIMEOUT} threadCount=\${THREADS_COUNT}</properties>
                  </hudson.tasks.Maven>
//                <hudson.tasks.Shell>
//                  <command>#!/bin/bash -l
//
//set -x #turn echo on
//mvn clean test site -Durl=\${TARGET_URL} -Dbrowser=\${BROWSER_NAME} -Dversion=\${BROWSER_VERSION} -Dtest=\${TESTS_SCOPE} -Dtimeout=\${TIMEOUT} -DthreadCount=\${THREADS_COUNT}
//                  </command>
//                  <configuredLocalRules/>
//                </hudson.tasks.Shell>
              </builders>
              <publishers>
                <hudson.tasks.ArtifactArchiver>
                  <artifacts>**/*.*</artifacts>
                  <allowEmptyArchive>true</allowEmptyArchive>
                  <onlyIfSuccessful>false</onlyIfSuccessful>
                  <fingerprint>false</fingerprint>
                  <defaultExcludes>true</defaultExcludes>
                  <caseSensitive>true</caseSensitive>
                  <followSymlinks>true</followSymlinks>
                </hudson.tasks.ArtifactArchiver>
                <hudson.plugins.testng.Publisher plugin="testng-plugin@835.v51ed3da_fcc35">
                  <reportFilenamePattern>**/testng-results.xml</reportFilenamePattern>
                  <escapeTestDescp>true</escapeTestDescp>
                  <escapeExceptionMsg>true</escapeExceptionMsg>
                  <failureOnFailedTestConfig>false</failureOnFailedTestConfig>
                  <showFailedBuilds>false</showFailedBuilds>
                  <unstableSkips>50</unstableSkips>
                  <unstableFails>50</unstableFails>
                  <failedSkips>100</failedSkips>
                  <failedFails>100</failedFails>
                  <thresholdMode>2</thresholdMode>
                </hudson.plugins.testng.Publisher>
              </publishers>
              <buildWrappers>
                <hudson.plugins.ws__cleanup.PreBuildCleanup plugin="ws-cleanup@0.46">
                  <deleteDirs>false</deleteDirs>
                  <cleanupParameter></cleanupParameter>
                  <externalDelete></externalDelete>
                  <disableDeferredWipeout>false</disableDeferredWipeout>
                </hudson.plugins.ws__cleanup.PreBuildCleanup>
                <hudson.plugins.timestamper.TimestamperBuildWrapper plugin="timestamper@1.27"/>
              </buildWrappers>
            </project>
        """
        Jenkins.instance.createProjectFromXML(getATExecutorJobName(), new ByteArrayInputStream(jobXml.getBytes('UTF-8')))
        echo "The '${getATExecutorJobName()}' job has been successfully created!"
    } else {
        echo "The '${getATExecutorJobName()}' job already exists!"
    }
}
return this