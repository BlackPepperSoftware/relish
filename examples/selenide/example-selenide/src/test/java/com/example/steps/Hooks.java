package com.example.steps;

import com.codeborne.selenide.Configuration;
import cucumber.api.CucumberOptions;
import cucumber.api.java.Before;

import java.io.IOException;

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
}
