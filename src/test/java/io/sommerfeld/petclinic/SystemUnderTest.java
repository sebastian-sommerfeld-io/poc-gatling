package io.sommerfeld.petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

/**
 * The SystemUnderTest class provides methods to interact with the application
 * which is targeted by the load test.
 */
public final class SystemUnderTest {

    public static final String BASE_URL = System.getenv("SYSTEM_UNDER_TEST");

    private static final int HTTP_OK = 200;

    private static final FeederBuilder.Batchable<String> testData = csv("owners.csv").random();

    /**
     * The httpProtocol method provides a builder for an HTTP protocol configuration
     * that is used in the Gatling simulation.
     *
     * @return object that represents the HTTP protocol and its headers
     */
    public static final HttpProtocolBuilder httpProtocol() {
        return http.baseUrl(SystemUnderTest.BASE_URL)
                .acceptHeader("text/html,application/xhtml+xml,application/xml")
                .acceptEncodingHeader("gzip, deflate")
                .acceptLanguageHeader("en-US,en;q=0.5")
                .userAgentHeader(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
    }

    private static final ChainBuilder requestHomepage() {
        return exec(http("GET homepage")
                .get("/")
                .check(status().is(HTTP_OK)));
    }

    private static final ChainBuilder requestFindOwnersPage() {
        return exec(http("GET find owners page")
                .get("/owners/find")
                .check(status().is(HTTP_OK)));
    }

    private static final ChainBuilder requestOwnersResultPage() {
        return feed(testData)
                .exec(http("GET owners result page")
                        .get("/owners?lastName=#{lastName}")
                        .check(status().is(HTTP_OK)));
    }

    private static final ChainBuilder requestOwnerDetailsPage() {
        return feed(testData)
                .exec(http("GET owner details page")
                        .get("/owners/#{id}")
                        .check(status().is(HTTP_OK)));
    }

    private static final ChainBuilder requestVeterinariansPage() {
        return exec(http("GET veterinarians page")
                .get("/vets")
                .check(status().is(HTTP_OK)));
    }

    /**
     * The walkAppScenario provides a Gatling scenario that walks through the
     * application by requesting pages in an order that simulates a user's journey
     * through the application.
     *
     * @return a ScenarioBuilder object that represents the walkAppScenario
     */
    public static final ScenarioBuilder walkAppScenario() {
        return scenario("Walk through the application")
                .exec(requestHomepage())
                .exec(requestFindOwnersPage())
                .exec(requestOwnersResultPage())
                .exec(requestOwnerDetailsPage())
                .exec(requestVeterinariansPage());
    }
}
