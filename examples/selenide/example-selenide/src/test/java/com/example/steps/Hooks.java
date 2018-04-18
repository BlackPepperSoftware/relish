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
