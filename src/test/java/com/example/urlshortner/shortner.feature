Feature: Shortner

    Background:
        Given url "http://localhost:7000/users"

    Scenario: Index user
        Given path "/1"
        When method get
        Then status 200
        And match response == { id: 1, firstName: "Bert", name: "Ernie", email: "bert@example.com" }