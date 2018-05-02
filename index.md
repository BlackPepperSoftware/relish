<img style="float: left; margin-right: 16px;" src="./images/Relish.png" width="28" height="38">

<h1 style="margin-top: -16px">What is Relish?</h1>

Relish is a Java library that is designed to reduce the amount of code needed to create UI/acceptance tests. Although it doesn't require it, Relish is intended to be used with a BDD tool like Cucumber to automate a user-interface.

There are currently two flavours of Relish:

-   `relish-selenide` For testing web applications.
    This requires the libraries `relish-core-....jar` and `relish-selenide-....jar`.
-   `relish-espresso` For testing Android applications.
    This requires the libraries `relish-core-....jar` and `relish-espresso-....aar`.

You can find the latest version of these libraries on the [releases page](https://github.com/davidgriffithsbp/relish/releases).

# Getting the libraries for Maven/gradle:

The libraries can be downloaded through http://jitpack.io, so, for example, this is how you might get them in gradle:

    repositories {
      mavenCentral()
      ....
      maven { url 'https://jitpack.io' }
    }
  
    dependencies {
      ....
      testCompile 'com.github.davidgriffithsbp:relish:0.0.119' # For version 0.0.119
    }

# Documentation

-   [Tutorial](./pages/tutorial.html)
