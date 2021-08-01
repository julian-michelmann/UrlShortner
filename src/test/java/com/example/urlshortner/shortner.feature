Feature: Shortner

    Background:
        Given url "http://localhost:7000"

    Scenario: Index urls
        Given path "/urls"
        When method get
        Then status 200
        And match response[*].id contains [1, 2]
        And match response contains And match response == { id: 1, uniqPath: "Aj8L3P", url: "https://www.axelspringer.com/en/brands/rolling-stone", hitCounter: 5, createCounter: 8 }

    Scenario: Show URL
        Given path "/urls/1"
        When method get
        Then status 200
        And match response[*].id contains [1, 2]
        And match response contains And match response == { id: 1, uniqPath: "Aj8L3P", url: "https://www.axelspringer.com/en/brands/rolling-stone", hitCounter: 5, createCounter: 8 }

    Scenario: Index filter for URL
        Given param filter = "url:https://www.axelspringer.com/en/brands/rolling-stone"
        Given path "/urls"
        When method get
        Then status 200
        And match response contains And match response == { id: 1, uniqPath: "Aj8L3P", url: "https://www.axelspringer.com/en/brands/rolling-stone", hitCounter: 5, createCounter: 8 }

    Scenario: Index filter for uniq path
        Given param filter = "uniqPath:Aj8L3P"
        Given path "/urls/1"
        When method get
        Then status 200
        And match response contains And match response == { id: 1, uniqPath: "Aj8L3P", url: "https://www.axelspringer.com/en/brands/rolling-stone", hitCounter: 5, createCounter: 8 }

    Scenario: Call create URL
        Given path "/urls"
        When request { url: "https://www.axelspringer.com/en/inside/newspapers-fresh-as-dew-and-still-fragrant" }
        When method post
        Then status 200
        And match response == { id: #present, uniqPath: "#present", url: "https://www.axelspringer.com/en/inside/newspapers-fresh-as-dew-and-still-fragrant", hitCounter: 0, createCounter: 1 }
        * def uniqPath = response.uniqPath
        * def url = response.url

        Given path "/" + uniqPath
        * configure followRedirects = false
        When method get
        Then status 302
        And match header Location == url

    Scenario: Call create multiple times create same uniqPath
        Given path "/urls"
        When request { url: "https://www.axelspringer.com/en/inside/more-than-just-scoring-goals" }
        When method post
        Then status 200
        And match response == { id: #present, uniqPath: "#present", url: "https://www.axelspringer.com/en/inside/more-than-just-scoring-goals", hitCounter: 0, createCounter: 1 }
        * def uniqPath = response.uniqPath

        Given path "/urls"
        When request { url: "https://www.axelspringer.com/en/inside/more-than-just-scoring-goals" }
        When method post
        Then status 200
        And match response.uniqPath == uniqPath

        Given path "/urls"
        When request { url: "https://www.axelspringer.com/en/inside/more-than-just-scoring-goals" }
        When method post
        Then status 200
        And match response.uniqPath == uniqPath

    Scenario: Different protocols produces different uniqPaths
        Given path "/urls"
        When request { url: "https://www.axelspringer.com/en/brands/stepstone-3" }
        When method post
        Then status 200
        * def uniqPath = response.uniqPath

        Given path "/urls"
        When request { url: "http://www.axelspringer.com/en/brands/stepstone-3" }
        When method post
        Then status 200
        And match response.uniqPath != uniqPath


    Scenario Outline: Call create with invalid url <url> produces error
        Given path "/urls"
        When request { url: <url> }
        When method post
        Then status 400
        And match response == "Error"

        Examples:
            | url                                                             |
            | www.axelspringer.com/en/inside/more-than-just-scoring-goals     |
            | https://                                                        |
            | https://www.axelspringer/en/inside/more-than-just-scoring-goals |

    Scenario: Hit counter works
        Given path "/K1fmF8"
        * configure followRedirects = false
        Then status 302

        Given path "/K1fmF8"
        * configure followRedirects = false
        Then status 302

        Given path "/K1fmF8"
        * configure followRedirects = false
        Then status 302

        Given path "/K1fmF8"
        * configure followRedirects = false
        Then status 302

        Given path "urls/2"
        When method get
        Then status 200
        And match response.hitCounter = 4


