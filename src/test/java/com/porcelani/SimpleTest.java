package com.porcelani;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.containsString;

public class SimpleTest {

    @Test
    public void should_get_in_goggle() {

        get("http://www.google.com.br")
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("Pesquisa Google"));
    }

}
