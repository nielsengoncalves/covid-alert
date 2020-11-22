package com.audibene.covidalert.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AlertLevel {
    @JsonProperty("red") RED,
    @JsonProperty("yellow") YELLOW,
    @JsonProperty("green") GREEN
}
