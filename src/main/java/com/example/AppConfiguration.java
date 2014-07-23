package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class AppConfiguration extends Configuration {

    @JsonProperty
    private String template;
}