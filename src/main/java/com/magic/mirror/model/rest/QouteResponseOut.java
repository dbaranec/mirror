package com.magic.mirror.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QouteResponseOut {

    String quoteText;
    String quoteAuthor;
}
