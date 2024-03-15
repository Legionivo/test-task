**Table of Contents**
<!-- TOC -->
* [Example of Selenide, JUnit5, Allure](#example-of-selenide-junit5-allure)
  * [Framework](#framework)
  * [How To Use](#how-to-use)
    * [Clone Repository](#clone-repository)
    * [Configuration Files](#configuration-files)
    * [Run Tests With Gradle](#run-tests-with-gradle)
      * [Perform On Browsers](#perform-on-browsers)
      * [Filter Tests](#filter-tests)
    * [Allure Report](#allure-report)
      * [Overview](#overview)
      * [Categories](#categories)
      * [Behaviors](#behaviors)
<!-- TOC -->

# Example of project with Selenide, JUnit5, Allure

## Requirements:
JDK 17

Gradle 8(optional, wrapper is included in project)

## Framework
> **Gradle**: <em>8.6 - Build Tool</em> <br>
> **Selenide**: <em>7.2.0 - WebDriver wrapper</em> <br>
> **Rest-assured**: <em>5.4.0 - Api testing</em> <br>
> **Allure**: <em>2.25.0 - Report Framework</em> <br>
> **JUnit5 - Jupiter**: <em>5.10.2 - Testing Framework</em> <br>
> **Owner**: <em>1.0.12 - Configuration management</em> <br>

## How To Use
### Clone Repository
> `$ git clone https://github.com/Legionivo/test-task.git`

### Run Tests With Gradle
-  Run tests(Linux/MacOS commands):
* `./gradlew clean test` - run all tests
* `./gradlew clean test -Dtags=Sometag` - run tests filtered by "Sometag"
* `./gradlew clean test -Dexcludetags=Sometag` - exclude tests filtered by "Sometag"
* `./gradlew clean test -Dexcludetags=sometag1,sometag2` - exclude tests filtered by multiple  tags
* `./gradlew clean test -Dselenide.browser=browsername` - run tests in different browser
* `./gradlew clean test -Dbase.url=https://example.com` - configure BaseUrl of application
* `./gradlew clean test -Dselenide.remote=http://seleniumgrid.example.com:4444/wd/hub` - run tests in Selenium Hub

    - Additional command line arguments see in com.codeborne.selenide/Configuration

### Configuration Files
You can change values for your case.

1. [log4j2.xml](src/test/resources/log4j2.xml) <br>
    > Configuring log4j2 involves assigning the Level, defining Appender, and specifying Layout objects in a configuration file.
2. [config.properties](src/test/resources/config.properties) <br> [custom_config.properties](src/test/resources/custom_config.properties) <br>
    > Configuration settings. Settings can be set either via system property or programmatically. 
3. [junit-platform.properties](src/test/resources/junit-platform.properties) <br>
    > If a properties file with this name is present in the root of the classpath, it will be used as a source for configuration parameters. If multiple files are present, only the first one detected in the classpath will be used.
4. [allure.properties](src/test/resources/allure.properties) <br>
    > Configuring all Allure properties by passing classpath.

### Configuration management
The Owner is a Java library with the goal of minimizing the code required to handle application configuration via Java properties files. 
You can read more about usage [here.](https://matteobaccan.github.io/owner/docs/usage/)
My idea of using this library is to solve the problem when configuration files are overwritten by developers when pushing to the repository. 
Each developer often works with his own environment, uses different parameters, and it often happens that this configuration is sent to the main code branch. How can this be avoided? 
Everyone creates a custom_config.properties file and adds it to .gitignore. This file has a higher priority than the config.properties file. 
The owner library reads this file and applies the necessary parameters.


#### Perform On Browsers
* `./gradlew clean test -Dselenide.browser=firefox`

Browser can be:
- chrome
- edge
- firefox
- safari
- ie

<em>`Note`: If you run safari, you must enable the `Allow Remote Automation` option in Safari's Develop menu to control Safari via WebDriver.</em>

#### Filter Tests
You can filter tests by using option `--tests`. <br>
Giving values can be `TestPackage`, `TestClass`, `TestMethod`.
> `./gradlew test -Dselenide.browser=firefox --tests ExampleTest.passedTest`

### Allure Report
Task `allureReport` generates Allure report from `/build/allure-results` folder.
> `./gradlew allureReport`

* Open allure report:
> `./gradlew allureServe` - generate Allure report and open it in the default browser

#####  Do not try to open Allure report from explorer - it will not work.

<em>Otherwise, you can see below.</em>

#### Overview
![](images/allure-report-overview.png?raw=true)

#### Behaviors
![](images/allure-report-behaviors.png?raw=true)
