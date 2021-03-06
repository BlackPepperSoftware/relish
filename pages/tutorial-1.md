<img style="float: left; margin-right: 16px;" src="../images/Relish.png" width="28" height="38">

<h1 style="margin-top: -16px">Tutorial 1: Creating the project</h1>

^ [Tutorial](./tutorial.html) | [Create our tests](./tutorial-2.html) &gt;

Relish is in the very early stages of development, so some of these details are likely to change. You'll find all of the code for this worked example in the `examples/selenide/example-selenide` folder in the Git repo.

We're going to walk through the steps of creating a simple web application to manage and create tasks. The app itself is very simplistic and stores all of its data locally, but it will be complex enough to demonstrate how to use Relish with pretty much any web application.

Create a project for your test code. In a real application you would probably want to create this as some sort of checked-in module of the application itself. That way, the code and the tests can be committed together.

We will include a dependency to the Relish code, and we'll also add a few other test libraries that will allow us to use Relish with Cucumber. This is the `build.gradle` file we'll be using here:

    apply plugin: 'java'
    
    sourceCompatibility = 1.8
    
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    
    dependencies {
        testCompile group: 'junit', name: 'junit', version: '4.12'
        testCompile group: 'info.cukes', name: 'cucumber-java', version: '1.2.5'
        testCompile group: 'info.cukes', name: 'cucumber-junit', version: '1.2.5'
        testCompile group: 'com.codeborne', name: 'selenide', version: '4.8'
        testCompile group: 'org.hamcrest', name:'java-hamcrest', version: '2.0.0.0'
        testCompile 'com.github.davidgriffithsbp:relish:0.0.119' # For version 0.0.119
    }

This will download the libraries from http://jitpack.io If you want to get the latest version of Relish, take a look at the [list of releases](https://github.com/davidgriffithsbp/relish/releases).

We'll make the assumption that you'll have a file structure like this:

    PROJECT_DIRECTORY +
                      |
                      +- build.gradle
                      |
                      +- src/
                         |
                         +- test/
                         |  |
                         |  +- java/
                         |     |
                         |     +- com/example/
                         |        |
                         |        +- <Java code goes here>
                         |
                         +- resources/
                            |
                            +- features/
                               |
                               +- <Cucumber feature tests go here>

# Create a configuration class

We'll need to tell Cucumber about it's environment, and we'll do this with a class called `TestRunner`:

    package com.example;
    
    import cucumber.api.CucumberOptions;
    import cucumber.api.junit.Cucumber;
    import org.junit.runner.RunWith;
    
    @RunWith(Cucumber.class)
    @CucumberOptions(
            features= "classpath:features",
            glue={"com.example.steps"}
    )
    public class TestRunner {
    }

# Set up some hooks

*Hooks* are part of the glue that allows Cucumber to work. They can before and after each test scenario, and they are typically used to initialize the UI (for example, by saying how to use the web browser) and to clear up after a test has finished. This is the `Hooks.java` that we'll be using:

    package com.example.steps;
    
    import com.codeborne.selenide.Configuration;
    
    import org.openqa.selenium.WebDriverException;
    
    import java.io.IOException;
    
    import cucumber.api.CucumberOptions;
    import cucumber.api.java.After;
    import cucumber.api.java.Before;
    
    import static com.codeborne.selenide.Selenide.clearBrowserCookies;
    import static com.codeborne.selenide.Selenide.clearBrowserLocalStorage;
    
    @CucumberOptions(features = "features")
    public class Hooks {
        @Before
        public void before() throws IOException {
            Configuration.browser = "chrome";
            String property = System.getProperty("selenide.baseUrl");
            if (property == null) {
                Configuration.baseUrl = "http://localhost:8000";
            }
        }
    
        @After
        public void after() throws IOException {
            clearBrowserCookies();
            try {
                clearBrowserLocalStorage();
            } catch(WebDriverException wde) {
                System.err.println("Cannot clear local storage. Non browser test?");
            }
        }
    }

This just says that we're expecting our application to be running at `http://localhost:8000`, that we'll use Chrome for our tests, and that we'll clear out the cookies and cache at the end of the test. This is important for our trivial example app, because we'll storing all of the data it creates in cookies.

# Create a steps-file

Steps are the lines of code that tell Cucumber how to run your test. We're going to create a single steps-file called `SomeSteps.java`:

    package com.example.steps;
    
    public class SomeSteps
    {
    }
 

^ [Tutorial](./tutorial.html) | [Create our tests](./tutorial-2.html) &gt;
