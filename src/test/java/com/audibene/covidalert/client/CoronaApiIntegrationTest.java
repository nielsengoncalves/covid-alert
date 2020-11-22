package com.audibene.covidalert.client;

import com.audibene.covidalert.IntegrationTest;
import com.audibene.covidalert.model.Timeline;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.audibene.covidalert.util.ResourceFileUtil.readFileContentAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class CoronaApiIntegrationTest extends IntegrationTest {

    @Autowired
    private CoronaApi coronaApi;

    @Test
    void shouldGetDataByCountryAndDeserializeCorrectly() throws IOException {
        mockServerClient
                .when(request().withMethod("GET").withPath("/countries/BR"))
                .respond(response()
                        .withStatusCode(200)
                        .withBody(readFileContentAsString("sample_response_br.json"))
                );

        final var expectedResponse = CoronaApiResponse.builder()
                .data(
                        CoronaApiResponse.InternalData.builder()
                                .name("Brazil")
                                .population(201103330L)
                                .timeline(Lists.list(Timeline.builder().active(429375L).date("2020-11-21").build()))
                                .build()
                )
                .build();

        final var actualResponse = coronaApi.getByCountry("BR").execute().body();

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}