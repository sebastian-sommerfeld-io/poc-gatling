package io.sommerfeld.petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

// 1. small constant load
// 2. small constant load

// delegate as much as possible into dedicated functions
// --> ChainBuilders ?!?!?! maybe dedicated class Requests or AppEndpoints or SystemUnderTest or similar?
// --> SystemUnderTest could know its base URL and provide ChainBuilders for all endpoints

// use params for /owners?lastName=

// Refactor this class into ConstantLoadSimulation
// Create new class IncreasingLoadSimulation
// ??? does this result in 1 single report with all simulations ???

/**
 * TODO ...
 */
public class ConstantLoadSimulation extends Simulation {

    private static final String BASE_URL = System.getenv("SYSTEM_UNDER_TEST");
    private static final int HTTP_OK = 200;

    private static final Duration simDuration = Duration.ofSeconds(120);

    HttpProtocolBuilder httpProtocol = http.baseUrl(BASE_URL)
            .acceptHeader("text/html,application/xhtml+xml,application/xml")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .userAgentHeader(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

    ChainBuilder getHomepage = exec(http("GET homepage")
            .get("/")
            .check(status().is(HTTP_OK)));

    ChainBuilder getFindOwnersPage = exec(http("GET find owners page")
            .get("/owners/find")
            .check(status().is(HTTP_OK)));

    ScenarioBuilder scn = scenario("Constant Load Scenario")
            .exec(getHomepage)
            .exec(getFindOwnersPage);

    {
        System.out.println("================================================================================");
        System.out.println("System under test");
        System.out.println(BASE_URL);
        System.out.println("================================================================================");

        setUp(
                scn.injectOpen(
                        nothingFor(Duration.ofSeconds(10)),
                        constantUsersPerSec(50).during(simDuration)))
                .protocols(httpProtocol)
                .assertions(
                        global().failedRequests().percent().is(0.0),
                        forAll().responseTime().percentile3().lt(500) // Ensures 95th percentile response < 500ms
                );

    }

}
