# Diff

WAES assignment

To run:

    mvn spring-boot:run

The rest API will be available at:

    http://localhost:8080

To run unit tests:

    mvn test
    
To run integration tests:

    mvn verify   

## API

Post contents (Base64 encoded JSON)

    POST /v1/diff/{id}/left
    POST /v1/diff/{id}/right

Get the diff

    GET /v1/diff/{id}
    
## Request samples

    curl -X POST http://localhost:8080/v1/diff/1/left -d eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9
    
    curl -X POST http://localhost:8080/v1/diff/1/right -d eyJmaXJzdE5hbWUiOiAiVGlhZ28iLCAibGFzdE5hbWUiOiAiQm9ubyJ9
    
    curl -X GET http://localhost:8080/v1/diff/1
    
    Result: {"equals":true,"equalsInSize":true,"diff":[]}