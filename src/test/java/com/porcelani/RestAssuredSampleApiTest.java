package com.porcelani;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RestAssuredSampleApiTest {

    @BeforeClass
    public static void setUp() throws Exception {
        Main.startServer();
    }

    @AfterClass
    public static void finilize() throws Exception {
        Main.stoptServer();
    }

    @Test
    public void should_get_in_goggle() {
        get("http://www.google.com.br")
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("Pesquisa Google"));

        //or

        expect()
                .statusCode(200)
                .body(containsString("Pesquisa Google"))
                .when()
                .get("http://www.google.com.br");

        //or

        Response res = get("http://www.google.com.br");
        assertEquals(200, res.getStatusCode());
        String page = res.asString();
        assertTrue(page.contains("Pesquisa Google"));
    }


    @Test
    public void should_get_index() {
        get("/index.html")
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("New Inova Vitrine"));
    }

    @Test
    public void should_get_single_user_as_json() {
        get("/rest/service/single-user")
                .then()
                .statusCode(200)
                .body(
                        "email", equalTo("test@hascode.com"),
                        "firstName", equalTo("Tim"),
                        "lastName", equalTo("Testerman"),
                        "id", equalTo("1"));
    }

    @Test
    public void should_get_single_user_as_xml() {
        get("/rest/service/single-user/xml")
                .then()
                .statusCode(200)
                .body(
                        "user.email", equalTo("test@hascode.com"),
                        "user.firstName", equalTo("Tim"),
                        "user.lastName", equalTo("Testerman"),
                        "user.id", equalTo("1"));
    }

    @Test
    public void should_get_persons_as_json() {
//        [
//          {"foo":1, "bar":2 , "baz":3 },
//          {"foo":3, "bar":4 , "baz":5 }
//        ]

//        expect().body("bar",hasItems(2,4))  Order is not important

        get("/rest/service/persons/json")
                .then()
                .statusCode(200)
                .body("person.email", hasItems("dev@hascode.com", "test@hascode.com", "devnull@hascode.com"));
    }

    @Test
    public void should_get_persons_as_xml_using_groovy_closure() {
        String json = get("/rest/service/persons/json").asString();
        JsonPath jp = new JsonPath(json);
        jp.setRoot("person");
        Map person = jp.get("find {e -> e.email =~ /test@/}");
        assertEquals("test@hascode.com", person.get("email"));
        assertEquals("Tim", person.get("firstName"));
        assertEquals("Testerman", person.get("lastName"));
    }

    @Test
    public void should_get_persons_as_xml_using_xpath() {
        get("/rest/service/persons/xml")
                .then()
                .statusCode(200)
                .body(hasXPath("//*[self::person and self::person[@id='1'] and self::person/email[text()='test@hascode.com'] and self::person/firstName[text()='Tim'] and self::person/lastName[text()='Testerman']]"))
                .body(hasXPath("//*[self::person and self::person[@id='20'] and self::person/email[text()='dev@hascode.com'] and self::person/firstName[text()='Sara'] and self::person/lastName[text()='Stevens']]"))
                .body(hasXPath("//*[self::person and self::person[@id='11'] and self::person/email[text()='devnull@hascode.com'] and self::person/firstName[text()='Mark'] and self::person/lastName[text()='Mustache']]"));
    }

    @Test
    public void should_create_user() {
        final String email = "test@hascode.com";
        final String firstName = "Tim";
        final String lastName = "Tester";

        given()
                .parameters("email", email, "firstName", firstName, "lastName", lastName)
        .expect()
                .body("email", equalTo(email))
                .body("firstName", equalTo(firstName))
                .body("lastName", equalTo(lastName))
        .when()
                .get("/rest/service/user/create");
    }

    @Test
    public void should_status_not_found() {
        expect()
                .statusCode(404)
        .when().get("/rest/service/status/notfound");
    }

    @Ignore //TODO
    @Test
    public void should_authentication() {
        // we're not authenticated, service returns "401 Unauthorized"
        expect().statusCode(401).when().get("/rest/service/secure/person");

        // with authentication it is working
        expect().statusCode(200).when().with().authentication()
                .basic("admin", "admin").get("/rest/service/secure/person");
    }

    @Test
    public void testSetRequestHeaders() {
        expect().body(equalTo("TEST")).when().with().header("myparam", "TEST")
                .get("/rest/service/header/print");
        expect().body(equalTo("foo")).when().with().header("myparam", "foo")
                .get("/rest/service/header/print");
    }

    @Test
    public void testReturnedHeaders() {
        expect().headers("customHeader1", "foo", "anotherHeader", "bar").when()
                .get("/rest/service/header/multiple");
    }

    @Test
    public void testAccessSecuredByCookie() {
        expect().statusCode(403).when()
                .get("/rest/service/access/cookie-token-secured");
        given().cookie("authtoken", "abcdef").expect().statusCode(200).when()
                .get("/rest/service/access/cookie-token-secured");
    }

    @Test
    public void testModifyCookie() {
        expect().cookie("userName", equalTo("Ted")).when().with()
                .param("name", "Ted").get("/rest/service/cookie/modify");
        expect().cookie("userName", equalTo("Bill")).when().with()
                .param("name", "Bill").get("/rest/service/cookie/modify");
    }

    @Test
    public void testFileUpload() {
        final File file = new File(getClass().getClassLoader()
                .getResource("test.txt").getFile());
        assertNotNull(file);
        assertTrue(file.canRead());
        given().multiPart(file).expect()
                .body(equalTo("This is an uploaded test file.")).when()
                .post("/rest/service/file/upload");
    }

    @Test
    public void testRegisterParserForUnknownContentType() {
        RestAssured.registerParser("text/json", Parser.JSON);
        expect().body("test", equalTo(true)).when().get("/rest/service/detail/json");
    }

    @Test
    public void testSpecReuse() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("email", equalTo("test@hascode.com"));
        builder.expectBody("firstName", equalTo("Tim"));
        builder.expectBody("lastName", equalTo("Testerman"));
        builder.expectBody("id", equalTo("1"));
        ResponseSpecification responseSpec = builder.build();

        // now we're able to use this specification for this test
        expect().spec(responseSpec).when().get("/rest/service/single-user");

        // now re-use for another test that returns similar data .. you may
        // extend the specification with further tests as you wish
        final String email = "test@hascode.com";
        final String firstName = "Tim";
        final String lastName = "Testerman";

        expect().spec(responseSpec)
                .when()
                .with()
                .parameters("email", email, "firstName", firstName, "lastName",
                        lastName).get("/rest/service/user/create");
    }
}
