package com.example.urlshortner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateResponse {

    @JsonProperty("uniqPathPart")
    String uniqPathPart;

    public CreateResponse(String uniqPathPart) {
        this.uniqPathPart = uniqPathPart;
    }
}
