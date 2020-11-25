package com.wmdemo;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WiremockDemoApplication {
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(options().port(8089));
        wireMockServer.start();
        configureFor("localhost", 8089);
        System.out.println("Wiremock server started");

        stubFor(delete("/fine").willReturn(ok()));

        stubFor(get("/fine-with-body").willReturn(ok("body content")));

        stubFor(get("/json").willReturn(okJson("{ \"message\": \"Hello\" }")));

        stubFor(post("/redirect").willReturn(temporaryRedirect("/json")));

        stubFor(post("/sorry-no").willReturn(unauthorized()));

        stubFor(put("/status-only").willReturn(status(418)));

        stubFor(get(urlEqualTo("/some/thing"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withStatusMessage("Everything was just fine!")
                                .withHeader("Content-Type", "text/plain")
                                .withHeader("Accept-Language", "en-US")
                                .withBody("Response body"))
                                //.withBodyFile("path/to/myfile.xml"))
        );

        stubFor(any(urlPathEqualTo("/transfer"))
                .withHeader("Accept", containing("xml"))
                .withQueryParam("amount", equalTo("1000"))
                .withBasicAuth("user@example.com", "password")
                .withRequestBody(equalToXml("<transfer/>"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Response body")
                ));
    }
}
