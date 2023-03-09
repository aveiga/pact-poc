package com.aveiga.pactpocconsumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.cert.ocsp.Req;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "GuitarProvider", pactVersion = PactSpecVersion.V3, port = "8080")
public class ContractTests {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Pact(provider="GuitarProvider", consumer="PocConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("guitars exist")
                .uponReceiving("get all guitars")
                .path("/guitars")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(PactDslJsonArray
                        .arrayEachLike()
                            .stringType("brand", "Fender")
                            .stringType("model", "Strat")
                        .closeObject())
                .toPact();
    }

    @Test
    void returnsOk(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/guitars").execute().returnResponse();
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));
    }

    @Test
    void hasBody(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/guitars").execute().returnResponse();
        assertThat(httpResponse.getEntity().getContent().toString().length(), is(greaterThan(0)));
    }

    @Test
    void hasFenderGuitar(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/guitars").execute().returnResponse();
        String guitarListJson = EntityUtils.toString(httpResponse.getEntity());
        List<Guitar> guitarList = objectMapper.readValue(guitarListJson, new TypeReference<List<Guitar>>() {});

        assertThat(guitarList.get(0).brand(), is("Fender"));
        assertThat(guitarList.get(0).model(), is("Strat"));
    }
}
