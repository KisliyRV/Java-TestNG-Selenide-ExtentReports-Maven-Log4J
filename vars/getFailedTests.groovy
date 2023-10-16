String call() {
    //Need to be installed the https://plugins.jenkins.io/http_request/ plugin
    def response = httpRequest httpMode: 'GET', validResponseCodes: '200:404', url: "https://{REPLACE_WITH_JENKINS_HOST}/job/ATExecutor/ws/target/surefire-reports/testng-failed.xml"
    if(response.status == 200) {
        def xmlFile = new XmlSlurper().parseText(response.content.split("dtd\">")[1])
        return xmlFile.test.classes.class.'@name'.toString().replaceAll("ua\\.com\\.usource\\.tests\\.", ",").replaceFirst(",", "")
    } else {
        return null
    }
}