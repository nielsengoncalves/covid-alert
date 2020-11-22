package com.audibene.covidalert.controller;

import com.audibene.covidalert.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

import static com.audibene.covidalert.util.ResourceFileUtil.readFileContentAsString;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class CoronaControllerIntegrationTest extends IntegrationTest {

    @Nested
    class GetAlertLevel {

        @Test
        void shouldReturn200WhenCountryIsFound() throws IOException, JSONException {
            mockServerClient
                    .when(request().withMethod("GET").withPath("/countries/BR"))
                    .respond(response()
                            .withStatusCode(200)
                            .withBody(readFileContentAsString("sample_response_br.json"))
                    );

            final var expectedResponse = "{\"countryName\":\"Brazil\",\"alertLevel\":\"green\"}";

            final var actualResponse = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("api/BR/2020-11-22")
                    .then()
                    .statusCode(200)
                    .extract()
                    .body().asString();

            JSONAssert.assertEquals(expectedResponse, actualResponse, true);
        }

        @Test
        void shouldReturn404WhenCountryIsNotFound() {
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("api/UU/2020-11-22")
                    .then()
                    .statusCode(404);
        }
    }
}