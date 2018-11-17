package com.magic.mirror.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QouteResponse {

    String quoteText;
    String quoteAuthor;

}
