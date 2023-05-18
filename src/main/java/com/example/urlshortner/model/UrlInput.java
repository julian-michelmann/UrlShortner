package com.example.urlshortner.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Setter
@Getter
public class UrlInput {
    
    @URL(message = "The URL you provided is invalid")
    private String url;
}
