This is an example project using the `relish-selenide` library.

# Running it

1. Install the `chromedriver` on your machine and add it to your `PATH` (if you want to use a different driver, edit `Hooks.java`)
2. Start a web server to display the pages in the `website` directory. For example, if you have `python` installed on your machine, change into the `website` directory and type `python -m SimpleHTTPServer`
3. Edit the `Hooks.java` class to match the base URL of your web site. It defaults to `http://localhost:8000`
4. Run the tests from the main project directory with `./gradlew examples:selenide:example-selenide:test`
