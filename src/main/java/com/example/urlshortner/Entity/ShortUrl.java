package com.example.urlshortner.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "The URL to be shortened", example = "https://www.axelspringer.com/en/inside/eye-on-everything-a-roller-coaster-ride-at-the-spandau-printing-house")
    @NotBlank(message = "URL must be set")
    String longUrl;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String shortPath;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long hitCounter;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Long createCounter;
}
