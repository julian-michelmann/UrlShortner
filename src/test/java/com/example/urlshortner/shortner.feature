Feature: Shortner

    Background:
        Given url "http://localhost:8090"

    Scenario: Creating a shorten url
        Given path "/urls"
        When request { url: "https://www.axelspringer.com/en/inside/newspapers-fresh-as-dew-and-still-fragrant" }
        When method post
        Then status 201
        And match response == {uniqPathPart:'#present'}
        And match header Location ==  '/aj8l3q'
        

    Scenario: Created url should be unique
        Given path "/urls"
        When request { url: "https://www.axelspringer.com/de/ax-press-release/microsoft-ceo-satya-nadella-erhaelt-axel-springer-award-2023" }
        When method post
        Then status 201
        * def uniqPathPart = response.uniqPathPart
    
        Given path "/urls"
        When request { url: "https://www.axelspringer.com/de/inside/tschuess-gruenes-as-zeichen" }
        When method post
        Then status 201
        Then match response.uniqPathPart != uniqPathPart

    Scenario: Do not create new shorten url for existing url
        Given path "/urls"
        When request { url: "https://www.axelspringer.com/de/inside/wie-der-vater-so-der-sohn" }
        When method post
        Then status 201
        * def uniqPathPart = response.uniqPathPart

        Given path "/urls"
        When request { url: "https://www.axelspringer.com/de/inside/wie-der-vater-so-der-sohn" }
        When method post
        Then status 201
        Then match response.uniqPathPart == uniqPathPart
    
    Scenario: Get redirected by shorten url
        * configure followRedirects = false
        Given path "/AJ8L3P"
        When method get
        Then status 308
        And match header Location == "https://www.axelspringer.com/de/marken/politico"

    Scenario: Create and get by url
        * configure followRedirects = false
        Given path "/urls"
        When request { url: "https://www.axelspringer.com/de/marken/politico/protocol" }
        When method post
        Then status 201
        * def uniqPathPart = response.uniqPathPart
        
        Given path "/" + uniqPathPart
        When method get
        Then status 308
        And match header Location == "https://www.axelspringer.com/de/marken/politico/protocol"
        
    Scenario: Try to get a none existing url
        Given path "/KM8L3P"
        When method get
        Then status 404
        And match response.message == "Url could not be found"

    Scenario: Get an 400 when trying to crate from an invalid url
        Given path "/urls"
        When request { url: "foo" }
        When method post
        Then status 400
        And assert response.errors.length == 1
        And match response.errors[0] == "The URL you provided is invalid"

    Scenario: http url are valid
        Given path "/urls"
        When request { url: "http://www.axelspringer.com/de/inside/wie-der-vater-so-der-sohn" }
        When method post
        Then status 201
        And match response.uniqPathPart == '#present'
