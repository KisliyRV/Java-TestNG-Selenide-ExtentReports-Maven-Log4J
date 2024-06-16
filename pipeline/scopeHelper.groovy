import hudson.model.*
import hudson.util.*

static String getFailedTests() {
    def copiedFile = copyArtifacts(projectName: 'ATExecutor', filter: 'target/surefire-reports/testng-failed.xml')

    if (copiedFile) {
        def xmlFile = new XmlSlurper().parse(copiedFile)
        return xmlFile.test.classes.class.'@name'.toString().replaceAll("ua\\.com\\.usource\\.tests\\.", ",").replaceFirst(",", "")
    } else {
        return null
    }
}
return this